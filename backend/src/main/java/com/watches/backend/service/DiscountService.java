package com.watches.backend.service;

import com.watches.backend.Dto.discount.CreateDiscountDto;
import com.watches.backend.Repositories.DiscountRepository;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.Discount;
import com.watches.backend.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class DiscountService {

    private final DiscountRepository repository;
    private final ProductService productService;

    public CompletableFuture<Discount> createAsync(@RequestBody CreateDiscountDto discountDto){
        List<Product> products = new ArrayList<>();
        for(Long id : discountDto.getProduct()){
            productService.findByIdAsync(id).thenAccept(products::add);
        }

        Discount discount = new Discount();
        discount.setProducts(new HashSet<>());
        for(Product product : products){
            if(product.getDiscount() != null){
                throw CException.conflict("Product with id " + product.getId() + " already have a discount");
            }
            discount.addProduct(product);
        }

        discount.setDiscount(discountDto.getDiscountPercentage() / 100);
        discount.setEndDate(discountDto.getExpireAt());
        repository.save(discount);

        for(Product product : products){
            product.setDiscount(discount);
            productService.saveAfterDiscount(product);
        }

        return CompletableFuture.completedFuture(discount);
    }

    public CompletableFuture<Discount> findByProductIdAsync(Long productId){
        Product product = productService.findByIdAsync(productId).join();
        if(product.getDiscount() == null){
            throw CException.conflict("Product with id " + product.getId() + " does not have a discount");
        }
        return CompletableFuture.completedFuture(product.getDiscount());
    }

    public void removeProductsAsync(List<Long> products){
        List<Product> prod = new ArrayList<>();
        for(Long id : products){
            productService.findByIdAsync(id).thenAccept(prod::add);
        }

        for(Product product : prod){
            if(product.getDiscount() == null){
                throw CException.conflict("Product with id " + product.getId() + " does not have a discount");
            }
            Discount discount = product.getDiscount();
            boolean flag = discount.removeProduct(product);
            if(!flag){
                throw CException.conflict("Product with id " + product.getId() + " does not have a discount");
            }
        }

        for(Product product : prod){
            repository.save(product.getDiscount());
            product.setDiscount(null);
            productService.saveAfterDiscount(product);
        }

    }

    public CompletableFuture<Discount> deleteByIdAsync(long id) {
        Discount discount = repository.findById(id)
                .orElseThrow(() -> CException.notFound(Discount.class, "id", id));

        discount.setExpired(true);
        repository.save(discount);
        return CompletableFuture.completedFuture(discount);
    }

    public CompletableFuture<List<Discount>> findAllAsync() {
        List<Discount> discounts = repository.findAll();
        return CompletableFuture.completedFuture(discounts);
    }

    public CompletableFuture<Discount> findById(Long id) {
        Discount discount = repository.findById(id)
                .orElseThrow(() -> CException.notFound(Discount.class, "id", id));
        return CompletableFuture.completedFuture(discount);
    }
}

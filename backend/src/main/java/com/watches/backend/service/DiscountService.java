package com.watches.backend.service;

import com.watches.backend.Dto.DiscountDto.CreateDiscountDto;
import com.watches.backend.Repositories.DiscountRepository;
import com.watches.backend.exceptions.DiscountNotFoundException;
import com.watches.backend.mappers.DiscountMapper;
import com.watches.backend.model.Discount;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class DiscountService {

    private final DiscountRepository repository;
    private final ProductService productService;

    public DiscountService(DiscountRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public CompletableFuture<List<Discount>> findAllAsync(){
        return CompletableFuture.completedFuture(repository.findAll());
    }

    public CompletableFuture<Discount> findByIdAsync(Long id){
        return CompletableFuture.completedFuture(
                repository.findById(id)
                        .orElseThrow(() ->
                                new DiscountNotFoundException(id)
                        )
        );
    }

//    public CompletableFuture<Discount> findByProductIdAsync(Long productId){
//
//        productService.findByIdAsync(productId); // checks if the product exists or not
//
//        Discount discount = repository.findAll()
//                        .stream()
//                        .filter(d -> Objects.equals(d.getProduct().getId(), productId))
//                        .toList()
//                        .get(0);
//
//        if(Objects.isNull(discount)){
//            throw new DiscountNotFoundException(productId);
//        }
//        return  CompletableFuture.completedFuture(discount);
//    }

    public CompletableFuture<Discount> createAsync(CreateDiscountDto discountDto){
        Discount discount = DiscountMapper.createToDiscount(discountDto);

        return CompletableFuture.completedFuture(
                repository.save(discount)
        );
    }

    public void deleteByIdAsync(Long id) {
        CompletableFuture<Discount> discount = this.findByIdAsync(id);
        discount.thenAccept(
                repository::delete
        );
    }

//    public void deleteByProductIdAsync(Long productId){
//        CompletableFuture<Discount> discount = this.findByProductIdAsync(productId);
//        discount.thenAccept(repository::delete);
//    }

}

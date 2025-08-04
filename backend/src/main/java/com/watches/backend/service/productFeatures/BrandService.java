package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.BrandRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.productFeatures.Brand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class BrandService {

    private final BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Brand> create(String brand) {
        Brand b = getByBrand(brand);
        if (b == null) {
            b = new Brand(brand);
            repository.save(b);
        }
        return CompletableFuture.completedFuture(b);
    }

    private Brand getByBrand(String brand) {
        return repository.findAll()
                .stream()
                .filter(b ->
                        b.getName()
                                .equals(brand)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> getAll() {
        Set<String> res = repository.findAllDistinctBrands();

        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> getByProductId(Long id) {
        String res = repository.findByProductId(id);
        if(Utils.isNullOrWhiteSpace(res)) {
            throw new RuntimeException("Either Product does not exist or product does not have a brand");
        }
        return CompletableFuture.completedFuture(res);
    }

}

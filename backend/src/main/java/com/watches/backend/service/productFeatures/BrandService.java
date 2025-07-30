package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.BrandRepository;
import com.watches.backend.model.productFeatures.Brand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Async
public class BrandService {

    private final BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Brand> create(String brand) {
        Brand b = new Brand(brand);
        repository.save(b);
        return CompletableFuture.completedFuture(b);
    }
}

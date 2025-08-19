package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.Brand;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class BrandService {

    private final DynamicQueryRepository<Brand> repository;

    @Transactional
    public CompletableFuture<Brand> create(String brand) {
        brand = Utils.normalizeString(brand);
        Brand b = getByBrand(brand);
        if (b == null) {
            b = new Brand(brand);
            b = repository.save(b);
        }
        return CompletableFuture.completedFuture(b);
    }

    private Brand getByBrand(String brand) {
        return repository.findAll(Brand.class)
                .stream()
                .filter(b ->
                        b.getName()
                                .equals(brand)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> getAll() {
        Set<String> res = repository.getDistinctValues(Brand.class, "name");

        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> getByProductId(Long id) {
        String res = repository.findByProductId("brand", "name",id);
        if(Utils.isNullOrWhiteSpace(res)) {
            throw CException.notFound(Brand.class, "productId", id);
        }
        return CompletableFuture.completedFuture(res);
    }

}

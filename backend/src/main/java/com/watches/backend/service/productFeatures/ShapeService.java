package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.Shape;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
@AllArgsConstructor
public class ShapeService {

    private final DynamicQueryRepository<Shape> repository;

    @Transactional
    public CompletableFuture<Shape> create(String shape) {
        shape = Utils.normalizeString(shape);
        Shape s = getByShape(shape);
        if(s == null){
            s = new Shape(shape);
            s = repository.save(s);
        }
        return CompletableFuture.completedFuture(s);
    }

    private Shape getByShape(String shape) {
        return repository.findAll(Shape.class)
                .stream()
                .filter(s ->
                        s.getName()
                                .equals(shape)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.getDistinctValues(Shape.class, "name");
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId("shape", "name", productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw CException.notFound(Shape.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }

}

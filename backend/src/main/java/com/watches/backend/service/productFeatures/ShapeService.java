package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.ShapeRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.productFeatures.Shape;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
public class ShapeService {

    private final ShapeRepository repository;

    public ShapeService(ShapeRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Shape> create(String shape) {
        Shape s = getByShape(shape);
        if(s == null){
            s = new Shape(shape);
            repository.save(s);
        }
        return CompletableFuture.completedFuture(s);
    }

    private Shape getByShape(String shape) {
        return repository.findAll()
                .stream()
                .filter(s ->
                        s.getName()
                                .equals(shape)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.findAllDistinctShapes();
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId(productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw new RuntimeException("Either product does not exist or Product does not have a shape");
        }
        return CompletableFuture.completedFuture(res);
    }

}

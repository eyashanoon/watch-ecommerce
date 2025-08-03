package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.ShapeRepository;
import com.watches.backend.model.productFeatures.Shape;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
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
                        s.getShape()
                                .equals(shape)
                )
                .findFirst()
                .orElse(null);
    }

}

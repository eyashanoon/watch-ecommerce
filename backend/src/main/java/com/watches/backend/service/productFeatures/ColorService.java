package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.ColorRepository;
import com.watches.backend.model.Product;
import com.watches.backend.model.productFeatures.Color;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ColorService {

    private final ColorRepository repository;

    public ColorService(ColorRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Color> create(String part, String handsColor) {
        Color color = new Color(part, handsColor);
        repository.save(color);
        return CompletableFuture.completedFuture(color);
    }

    public void update(Color color) {
        repository.save(color);
    }
}

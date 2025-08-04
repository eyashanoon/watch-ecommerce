package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.ColorRepository;
import com.watches.backend.model.productFeatures.Color;
import com.watches.backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorService {

    private final ColorRepository repository;

    public CompletableFuture<Color> create(String part, String handsColor) {
        Color color = new Color(part, handsColor);
        repository.save(color);
        return CompletableFuture.completedFuture(color);
    }

    public CompletableFuture<Map<String, String>> getByProductId(Long productId) {
        Map<String, String> res = repository.findAll()
                .stream()
                .filter(c -> c.getProduct() != null && c.getProduct().getId().equals(productId))
                .collect(Collectors.toMap(
                        Color::getWatchPart,
                        Color::getColor
                ));

        if(res.isEmpty()) {
            throw new RuntimeException("Either product does not exist or product does not have colors { product Id = " + productId + " }");
        }
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<Map<String, Set<String>>> getAllColors(){
        Map<String, Set<String>> res = repository.findAll()
                .stream()
                .filter(c -> c.getColor() != null && c.getWatchPart() != null)
                .collect(Collectors.groupingBy(
                        Color::getWatchPart,
                        Collectors.mapping(Color::getColor, Collectors.toSet())
                ));
        return CompletableFuture.completedFuture(res);
    }


    public void update(Color color) {
        repository.save(color);
    }
}

package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.Color;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorService {

    private final DynamicQueryRepository<Color> repository;

    public CompletableFuture<Color> create(String part, String handsColor) {
        handsColor = Utils.normalizeString(handsColor);
        part = Utils.normalizeString(part);
        Color color = new Color(part, handsColor);
        repository.save(color);
        return CompletableFuture.completedFuture(color);
    }

    public CompletableFuture<Map<String, String>> getByProductId(Long productId) {
        Map<String, String> res = repository.findAll(Color.class)
                .stream()
                .filter(c -> c.getProduct() != null && c.getProduct().getId().equals(productId))
                .collect(Collectors.toMap(
                        Color::getWatchPart,
                        Color::getColor
                ));

        if(res.isEmpty()) {
            throw CException.notFound(Color.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<Map<String, Set<String>>> getAllColors(){
        Map<String, Set<String>> res = repository.findAll(Color.class)
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

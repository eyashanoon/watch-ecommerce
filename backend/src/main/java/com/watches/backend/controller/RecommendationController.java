package com.watches.backend.controller;
import com.watches.backend.model.Product;
import com.watches.backend.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService service;

    @PostMapping
    List<Recommendation> getRecommendations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if(authentication == null || !authentication.isAuthenticated()){
            username = "";
        }else{
            username = authentication.getName();
        }

        List<Product> products = service.getRecommendations(username);
        return products.stream().map(Recommendation::to).toList();
    }

    private record Recommendation(Long id, String name){
        public static Recommendation to(Product product){
            return new Recommendation(product.getId(), product.getName());
        }
    }

}

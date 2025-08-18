package com.watches.backend.controller.featuresController;

import com.watches.backend.service.productFeatures.BandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/product/band")
@AllArgsConstructor
public class BandController {

    private final BandService bandService;

    @GetMapping
    public Set<String> getAll() {
        return bandService.findAll().join();
    }

    @GetMapping("/{productId}")
    public String getByProductId(@PathVariable Long productId){
        return bandService.findByProductId(productId).join();
    }

}

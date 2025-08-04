package com.watches.backend.controller.featuresController;

import com.watches.backend.service.productFeatures.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/product/brand")
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public Set<String> getAllBrands(){
        return brandService.getAll().join();
    }

    @GetMapping("/{productId}")
    public String getBrandById(@PathVariable Long productId){
        return brandService.getByProductId(productId).join();
    }

}

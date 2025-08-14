package com.watches.backend.controller.featuresController;

import com.watches.backend.service.productFeatures.NumberingFormatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/product/numbering_format")
@AllArgsConstructor
public class NumberingFormatController {

    private final NumberingFormatService numberingFormatService;

    @GetMapping
    Set<String> getAll(){
        return numberingFormatService.findAll().join();
    }

    @GetMapping("/{productId}")
    String getByProductId(@PathVariable Long productId){
        return numberingFormatService.findByProductId(productId).join();
    }

}

package com.watches.backend.controller.featuresController;

import com.watches.backend.service.productFeatures.DisplayTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/product/display_type")
@AllArgsConstructor
public class DisplayTypeController {

    private final DisplayTypeService displayTypeService;

    @GetMapping
    Set<String> getAll(){
        return displayTypeService.findAll().join();
    }

    @GetMapping("/{productId}")
    String getByProductId(@PathVariable("productId") Long productId){
        return displayTypeService.findByProductId(productId).join();
    }

}

package com.watches.backend.controller.featuresController;

import com.watches.backend.service.productFeatures.ShapeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/product/shape")
@AllArgsConstructor
public class ShapeController {

    private final ShapeService shapeService;

    @GetMapping
    Set<String> findAll(){
        return shapeService.findAll().join();
    }

    @GetMapping("/{productId}")
    String findById(@PathVariable Long productId){
        return shapeService.findByProductId(productId).join();
    }

}

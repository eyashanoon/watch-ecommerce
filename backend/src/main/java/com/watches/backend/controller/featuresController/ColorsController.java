package com.watches.backend.controller.featuresController;


import com.watches.backend.service.productFeatures.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.Map;

@RestController
@RequestMapping("/api/product/colors")
@AllArgsConstructor
public class ColorsController {

    private final ColorService colorService;

    @GetMapping("/{productId}")
    Map<String, String> getProductColors(@PathVariable Long productId){
        return colorService.getByProductId(productId).join();
    }

    @GetMapping
    Map<String, Set<String>> getAllColors(){
        return colorService.getAllColors().join();
    }

}

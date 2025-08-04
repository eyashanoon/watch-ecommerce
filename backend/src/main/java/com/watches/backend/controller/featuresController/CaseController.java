package com.watches.backend.controller.featuresController;

import com.watches.backend.service.productFeatures.CaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/product/case")
@AllArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @GetMapping
    Set<String> getAll(){
        return caseService.findAll().join();
    }

    @GetMapping("/{productId}")
    String getByProductId(@PathVariable("productId") Long productId){
        return caseService.findByProductId(productId).join();
    }

}

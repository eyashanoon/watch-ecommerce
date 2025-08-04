package com.watches.backend.controller.featuresController;

import com.watches.backend.Dto.featuresDto.ImageDto;
import com.watches.backend.mappers.FeaturesMapper;
import com.watches.backend.model.Image;
import com.watches.backend.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/product/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   // @PreAuthorize("hasRole('ADMIN')")
    ImageDto addImage(@Valid @RequestParam("ProductId") Long productId, @RequestParam("image") MultipartFile file) {
        CompletableFuture<Image> image = imageService.create(productId, file);
        return FeaturesMapper.imageToDto(image.join());
    }

    @GetMapping("/{id}")
    ImageDto getImageById(@PathVariable Long id){
        CompletableFuture<Image> image = imageService.getImageById(id);
        return FeaturesMapper.imageToDto(image.join());
    }

    @GetMapping("/product/{productId}")
    ImageDto getImageByProductId(@PathVariable Long productId){
        CompletableFuture<Image> image = imageService.getImageByProductId(productId);
        return FeaturesMapper.imageToDto(image.join());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    ImageDto updateImage(@Valid @RequestParam("productId") Long productId, @Valid @RequestParam("image") MultipartFile file){
        CompletableFuture<Image> image = imageService.update(productId, file);
        return FeaturesMapper.imageToDto(image.join());
    }

    @DeleteMapping("/{productId}")
    //@PreAuthorize("hasRole('ADMIN')")
    void delete(@Valid @PathVariable("productId") Long productId){

        imageService.delete(productId);
    }

}

package com.watches.backend.controller.featuresController;

import com.watches.backend.Dto.featuresDto.ImageDto;
import com.watches.backend.mappers.FeaturesMapper;
import com.watches.backend.model.Image;
import com.watches.backend.service.ImageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/product/image")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('OWNER') || hasRole('CREATE_PRODUCT')")
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
    List<ImageDto> getImageByProductId(@PathVariable Long productId){
        CompletableFuture<List<Image>> image = imageService.getImageByProductId(productId);
        return image.join().stream().map(FeaturesMapper::imageToDto).toList();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('DELETE_PRODUCT')")
    void deleteId(@Valid @PathVariable Long id){
        imageService.deleteById(id);
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasRole('OWNER') || hasRole('DELETE_PRODUCT')")
    void delete(@Valid @PathVariable Long productId){
        imageService.delete(productId);
    }

}

package com.watches.backend.service;

import com.watches.backend.Repositories.ImageRepository;
import com.watches.backend.model.Image;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class ImageService {

    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    private static Image createImageObject(String fileName, byte[] fileContent) {
        Image image = new Image();
        image.setFilename(fileName);
        image.setData(Base64.getEncoder().encodeToString(fileContent));
        return image;
    }

    public CompletableFuture<Image> create(MultipartFile image){
        try {
            Image newImage = createImageObject(
                    image.getOriginalFilename(), image.getBytes()
            );
            return CompletableFuture.completedFuture(
                    repository.save(newImage)
            );
        }catch (Exception e){
            return CompletableFuture.completedFuture(null);
        }
    }

    public CompletableFuture<Image> getImageById(Long imageId) {
        repository.findById(imageId);
        return CompletableFuture.completedFuture(repository.findById(imageId).orElse(null));
    }
}

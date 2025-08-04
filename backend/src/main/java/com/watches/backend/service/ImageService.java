package com.watches.backend.service;

import com.watches.backend.Repositories.ImageRepository;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class ImageService {

    private final ImageRepository repository;
    private final ProductService service;

    private static Image createImageObject(String fileName, byte[] fileContent) {
        Image image = new Image();
        image.setFilename(fileName);
        image.setData(Base64.getEncoder().encodeToString(fileContent));
        return image;
    }

    public CompletableFuture<Image> create(Long productId, MultipartFile image){
        try {
            Image newImage = createImageObject(
                    image.getOriginalFilename(), image.getBytes()
            );
            Product product = service.findByIdAsync(productId).get();
            newImage.setProduct(product);
            repository.save(newImage);
            service.setImage(product, newImage);
            return CompletableFuture.completedFuture(newImage);
        }catch (Exception e){
            return CompletableFuture.completedFuture(null);
        }
    }

    public CompletableFuture<Image> getImageById(Long imageId) {
        Image image = repository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with Id " + imageId));
        return CompletableFuture.completedFuture(image);
    }

    public CompletableFuture<Image> getImageByProductId(Long productId) {
        return CompletableFuture.completedFuture(
                service.findByIdAsync(productId)
                        .join()
                        .getImage()
        );
    }

    public CompletableFuture<Image> update(Long productId, MultipartFile image) {
        try {
            Image img = getImageByProductId(productId).join();
            img.setFilename(image.getOriginalFilename());
            img.setData(Base64.getEncoder().encodeToString(image.getBytes()));
            return CompletableFuture.completedFuture(repository.save(img));
        }catch (Exception e){
            throw new RuntimeException("There was an exception while updating the image");
        }
    }

    public void delete(Long productId){
        CompletableFuture<Image> image = getImageByProductId(productId);
        image.thenAccept(repository::delete);
        image.join();
    }

}

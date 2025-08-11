package com.watches.backend.service;

import com.watches.backend.Repositories.ImageRepository;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.Image;
import com.watches.backend.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        image.setData(fileContent);
        return image;
    }

    private boolean validImage(MultipartFile image){
        try{
            BufferedImage img = ImageIO.read(image.getInputStream());
            return img != null;
        }catch (Exception e){
            return false;
        }
    }

    public CompletableFuture<Image> create(Long productId, MultipartFile image){
        try {
            if(validImage(image)){
                throw CException.badRequest(Image.class, "Provided file is not an image");
            }
            Image newImage = createImageObject(image.getOriginalFilename(), image.getBytes());
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
                .orElseThrow(() -> CException.notFound(Image.class, "id", imageId));
        return CompletableFuture.completedFuture(image);
    }

    public CompletableFuture<Image> getImageByProductId(Long productId) {
        Product product = service.findByIdAsync(productId).join();
        if(product == null){
            throw CException.notFound(Product.class, "id", productId);
        }
        Image img = product.getImage();
        if (img == null) {
            throw CException.notFound(Image.class, "Product id", productId);
        }
        return CompletableFuture.completedFuture(img);
    }

    public CompletableFuture<Image> update(Long productId, MultipartFile image) {
        try {
            if(validImage(image)){
                throw CException.badRequest(Image.class, "Provided file is not an image");
            }
            Image img = getImageByProductId(productId).join();
            img.setFilename(image.getOriginalFilename());
            img.setData(image.getBytes());
            return CompletableFuture.completedFuture(repository.save(img));
        }catch (Exception e){
            throw CException.unexpected(e);
        }
    }

    public void delete(Long productId){
        CompletableFuture<Image> image = getImageByProductId(productId);
        image.thenAccept(repository::delete);
        image.join();
    }

}

package com.watches.backend.controller;

import com.watches.backend.Dto.ProductDto.ProductDto;
import com.watches.backend.Dto.WishlistDto.CreateWishlistDto;
import com.watches.backend.Dto.WishlistDto.WishlistDto;
import com.watches.backend.Repositories.WishlistRepository;
import com.watches.backend.exceptions.WishlistNotFoundException;
import com.watches.backend.mappers.ProductMapper;
import com.watches.backend.mappers.WishlistMapper;
import com.watches.backend.model.Wishlist;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
public class WishlistController {

    private final WishlistRepository repository;

    public WishlistController(WishlistRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/wishlist/{id}")
    WishlistDto GetWishlist(@PathVariable Long id){
        return repository.findById(id).map(WishlistMapper::wishlistToDto)
                .orElseThrow(() -> new WishlistNotFoundException(id));
    }

    @PostMapping("/wishlist")
    Wishlist AddWishlist(@RequestBody CreateWishlistDto wishlistDto){
        return repository.save(WishlistMapper.createToWishlist(wishlistDto));
    }

    @PutMapping("/wishlist/add/{id}")
    void addProduct(@PathVariable Long id, @RequestBody ProductDto product){
        Optional<Wishlist> wishlistOpt = repository.findById(id);
        if(wishlistOpt.isPresent()){
            Wishlist wishlist = wishlistOpt.get();
            wishlist.getProducts().add(ProductMapper.DtoToProduct(product));
            repository.save(wishlist);
        }
    }

    @PutMapping("/wishlist/remove/{id}")
    void removeProduct(@PathVariable Long id, @RequestBody ProductDto product){
        Optional<Wishlist> wishlistOpt = repository.findById(id);
        if(wishlistOpt.isPresent()){
            Wishlist wishlist = wishlistOpt.get();
            wishlist.getProducts().remove(ProductMapper.DtoToProduct(product));
            repository.save(wishlist);
        }
    }

    @DeleteMapping("/wishlist/{id}")
    void deleteWishlist(@PathVariable Long id){
        repository.deleteById(id);
    }

}

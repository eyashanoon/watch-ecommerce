package com.watches.backend.service;

import com.watches.backend.Dto.DiscountDto.CreateDiscountDto;
import com.watches.backend.Repositories.DiscountRepository;
import com.watches.backend.exceptions.DiscountNotFoundException;
import com.watches.backend.mappers.DiscountMapper;
import com.watches.backend.model.Discount;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class DiscountService {

    private final DiscountRepository repository;

    public DiscountService(DiscountRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<List<Discount>> findAllAsync(){
        return CompletableFuture.completedFuture(repository.findAll());
    }

    public CompletableFuture<Discount> findByIdAsync(Long id){
        return CompletableFuture.completedFuture(
                repository.findById(id)
                        .orElseThrow(() ->
                                new DiscountNotFoundException(id)
                        )
        );
    }

    public CompletableFuture<Discount> create(CreateDiscountDto discountDto){
        Discount discount = DiscountMapper.createToDiscount(discountDto);

        return CompletableFuture.completedFuture(
                repository.save(discount)
        );
    }



}

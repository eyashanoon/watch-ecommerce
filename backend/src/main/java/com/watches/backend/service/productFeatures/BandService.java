package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.Band;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class BandService {

    private final DynamicQueryRepository<Band> repository;

    @Transactional
    public CompletableFuture<Band> create(String bandMaterial) {
        bandMaterial = Utils.normalizeString(bandMaterial);
        Band band = getByMaterial(bandMaterial);
        if(band == null){
            band = new Band(bandMaterial);
            band = repository.save(band);
        }
        return CompletableFuture.completedFuture(band);
    }

    private Band getByMaterial(String bandMaterial) {
        return repository.findAll(Band.class)
                .stream()
                .filter(band ->
                        band.getMaterial()
                                .equals(bandMaterial)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.getDistinctValues(Band.class, "material");
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId("band", "material", productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw CException.notFound(Band.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }

}

package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.BandRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.productFeatures.Band;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Async
public class BandService {

    private final BandRepository repository;

    public BandService(BandRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Band> create(String bandMaterial) {
        bandMaterial = Utils.normalizeString(bandMaterial);
        Band band = getByMaterial(bandMaterial);
        if(band == null){
            band = new Band(bandMaterial);
            repository.save(band);
        }
        return CompletableFuture.completedFuture(band);
    }

    private Band getByMaterial(String bandMaterial) {
        return repository.findAll()
                .stream()
                .filter(band ->
                        band.getMaterial()
                                .equals(bandMaterial)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.getAllDistinctBands();
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId(productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw new RuntimeException("Either Product does not exist or product does not have a band material");
        }
        return CompletableFuture.completedFuture(res);
    }

}

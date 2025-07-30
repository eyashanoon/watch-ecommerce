package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.BandRepository;
import com.watches.backend.model.productFeatures.Band;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Async
public class BandService {

    private final BandRepository repository;

    public BandService(BandRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Band> create(String bandMaterial) {
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
                        band.getBandMaterial()
                                .equals(bandMaterial)
                )
                .findFirst()
                .orElse(null);
    }

}

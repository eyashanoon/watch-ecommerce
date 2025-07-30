package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.CaseRepository;
import com.watches.backend.model.productFeatures.Case;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CaseService {

    private final CaseRepository repository;

    public CaseService(CaseRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Case> create(String caseMaterial) {
        Case c = new Case(caseMaterial);
        repository.save(c);
        return CompletableFuture.completedFuture(c);
    }
}

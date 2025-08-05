package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.CaseRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.productFeatures.Case;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
public class CaseService {

    private final CaseRepository repository;

    public CaseService(CaseRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Case> create(String caseMaterial) {
        caseMaterial = Utils.normalizeString(caseMaterial);
        Case c = getByMaterial(caseMaterial);
        if(c==null){
            c = new Case(caseMaterial);
            repository.save(c);
        }
        return CompletableFuture.completedFuture(c);
    }

    private Case getByMaterial(String material) {
        return repository.findAll()
                .stream()
                .filter(c ->
                        c.getMaterial()
                                .equals(material)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.findAllDistinctCases();
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId(productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw new RuntimeException("Either product does not exist or product does not have a case material");
        }
        return CompletableFuture.completedFuture(res);
    }

}

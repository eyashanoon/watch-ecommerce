package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.Case;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
@AllArgsConstructor
public class CaseService {

    private final DynamicQueryRepository<Case> repository;

    @Transactional
    public CompletableFuture<Case> create(String caseMaterial) {
        caseMaterial = Utils.normalizeString(caseMaterial);
        Case c = getByMaterial(caseMaterial);
        if(c==null){
            c = new Case(caseMaterial);
            c = repository.save(c);
        }
        return CompletableFuture.completedFuture(c);
    }

    private Case getByMaterial(String material) {
        return repository.findAll(Case.class)
                .stream()
                .filter(c ->
                        c.getMaterial()
                                .equals(material)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.getDistinctValues(Case.class, "material");
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId("aCase", "material" , productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw CException.notFound(Case.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }

}

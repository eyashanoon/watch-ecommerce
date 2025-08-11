package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.DisplayTypeRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.DisplayType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
public class DisplayTypeService {

    private final DisplayTypeRepository repository;

    public DisplayTypeService(DisplayTypeRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<DisplayType> create(String displayType) {
        displayType = Utils.normalizeString(displayType);
        DisplayType dto = getByType(displayType);
        if(dto == null) {
            dto = new DisplayType(displayType);
            repository.save(dto);
        }
        return  CompletableFuture.completedFuture(dto);
    }

    private DisplayType getByType(String displayType){
        return repository.findAll()
                .stream()
                .filter(d ->
                        d.getType()
                                .equals(displayType)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.findAllDistinctDisplayType();
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId(productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw CException.notFound(DisplayType.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }

}

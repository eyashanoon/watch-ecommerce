package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.DisplayType;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
@AllArgsConstructor
public class DisplayTypeService {

    private final DynamicQueryRepository<DisplayType> repository;


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
        return repository.findAll(DisplayType.class)
                .stream()
                .filter(d ->
                        d.getType()
                                .equals(displayType)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.getDistinctValues(DisplayType.class, "type");
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId("displayType", "type", productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw CException.notFound(DisplayType.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }

}

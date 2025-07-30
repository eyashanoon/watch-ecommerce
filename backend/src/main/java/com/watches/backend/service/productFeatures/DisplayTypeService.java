package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.DisplayTypeRepository;
import com.watches.backend.model.productFeatures.DisplayType;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DisplayTypeService {

    private final DisplayTypeRepository repository;

    public DisplayTypeService(DisplayTypeRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<DisplayType> create(String displayType) {
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

}

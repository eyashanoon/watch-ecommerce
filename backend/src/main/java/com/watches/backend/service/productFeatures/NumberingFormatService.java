package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.NumberingFormatRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.model.productFeatures.NumberingFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
public class NumberingFormatService {

    private final NumberingFormatRepository repository;

    public NumberingFormatService(NumberingFormatRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<NumberingFormat> create(String numberingFormat) {
        NumberingFormat nf = getByFormat(numberingFormat);
        if(nf == null){
            nf = new NumberingFormat(numberingFormat);
            repository.save(nf);
        }
        return CompletableFuture.completedFuture(nf);
    }

    private NumberingFormat getByFormat(String Format) {
        return repository.findAll()
                .stream()
                .filter(n ->
                        n.getFormat()
                                .equals(Format)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.findAllDistinctFormats();
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId(productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw new RuntimeException("Either product does not exist or product does not have a numbering format");
        }
        return CompletableFuture.completedFuture(res);
    }


}

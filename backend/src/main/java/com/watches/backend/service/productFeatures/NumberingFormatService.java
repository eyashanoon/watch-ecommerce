package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.productFeaturesRepositories.NumberingFormatRepository;
import com.watches.backend.model.productFeatures.NumberingFormat;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
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
                        n.getNumberingFormat()
                                .equals(Format)
                )
                .findFirst()
                .orElse(null);
    }

}

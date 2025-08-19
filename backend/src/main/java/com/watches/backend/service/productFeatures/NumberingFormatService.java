package com.watches.backend.service.productFeatures;

import com.watches.backend.Repositories.DynamicQueryRepository;
import com.watches.backend.helpers.Utils;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.model.productFeatures.NumberingFormat;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.Set;

@Service
@Async
@AllArgsConstructor
public class NumberingFormatService {

    private final DynamicQueryRepository<NumberingFormat> repository;

    @Transactional
    public CompletableFuture<NumberingFormat> create(String numberingFormat) {
        numberingFormat = Utils.normalizeString(numberingFormat);
        NumberingFormat nf = getByFormat(numberingFormat);
        if(nf == null){
            nf = new NumberingFormat(numberingFormat);
            nf = repository.save(nf);
        }
        return CompletableFuture.completedFuture(nf);
    }

    private NumberingFormat getByFormat(String Format) {
        return repository.findAll(NumberingFormat.class)
                .stream()
                .filter(n ->
                        n.getFormat()
                                .equals(Format)
                )
                .findFirst()
                .orElse(null);
    }

    public CompletableFuture<Set<String>> findAll(){
        Set<String> res = repository.getDistinctValues(NumberingFormat.class, "format");
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<String> findByProductId(Long productId){
        String res = repository.findByProductId("numberingFormat", "format", productId);
        if(Utils.isNullOrWhiteSpace(res)){
            throw CException.notFound(NumberingFormat.class, "productId", productId);
        }
        return CompletableFuture.completedFuture(res);
    }


}

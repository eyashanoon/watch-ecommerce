package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.NumberingFormat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumberingFormatRepository extends JpaRepository<NumberingFormat, String> {
}

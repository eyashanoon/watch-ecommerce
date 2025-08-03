package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.DisplayType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisplayTypeRepository extends JpaRepository<DisplayType, String> {
}

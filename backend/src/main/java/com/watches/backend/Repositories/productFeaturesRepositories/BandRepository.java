package com.watches.backend.Repositories.productFeaturesRepositories;

import com.watches.backend.model.productFeatures.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, String> {
}

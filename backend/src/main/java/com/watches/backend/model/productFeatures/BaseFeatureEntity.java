package com.watches.backend.model.productFeatures;

import com.watches.backend.model.Product;
import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class BaseFeatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}

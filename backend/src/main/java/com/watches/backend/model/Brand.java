package com.watches.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Brand extends BaseFeatureEntity {

    String feature;

}

package com.watches.backend.model;

import jakarta.persistence.Entity;

@Entity
public class Band extends BaseFeatureEntity {

    private String bandMaterial;
    private Boolean changeable;

}

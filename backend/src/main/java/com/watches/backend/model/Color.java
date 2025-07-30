package com.watches.backend.model;

import jakarta.persistence.Entity;

@Entity
public class Color extends BaseFeatureEntity {

    private String watchPart;
    private String color;

}

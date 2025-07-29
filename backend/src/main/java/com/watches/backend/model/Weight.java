package com.watches.backend.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;

@Entity
public class Weight extends BaseFeatureEntity {

    private Double weight;
}

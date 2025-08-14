package com.watches.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE,
    ALL;

    @JsonCreator
    public static Gender forValue(String value) {
        return value == null ? null : Gender.valueOf(value.toUpperCase());
    }

}

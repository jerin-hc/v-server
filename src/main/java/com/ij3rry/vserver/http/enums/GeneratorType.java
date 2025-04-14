package com.ij3rry.vserver.http.enums;

public enum GeneratorType {
    FILE,
    CONTROLLER;

    public static GeneratorType fromString(String type) {
        return GeneratorType.valueOf(type.toUpperCase());
    }
}

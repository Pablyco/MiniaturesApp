package com.example.miniatures.model.enums;

public enum MiniatureScale {
    SMALL_45MM("Miniaturas chicas hasta 45mm muy usadas en wargames"),
    MEDIUM_100MM("Miniaturas medianas hasta 100mm normales, figuras de accion, etc."),
    LARGE_170MM("Miniaturas grandes hasta 170mm, dragones, creaturas gigantes, etc.");

    private final String displayName;

    MiniatureScale(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

package com.example.miniatures.model.enums;

public enum MiniatureType {
    WARHAMMER("Warhammer 40k"),
    DND("Dungeons & Dragons"),
    SANRIO("Sanrio"),
    ANIME("Anime"),
    VIDEO_GAME("Videojuego"),
    CUSTOM("Personalizada"),
    OTHER("Otra");

    private final String displayName;

    MiniatureType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

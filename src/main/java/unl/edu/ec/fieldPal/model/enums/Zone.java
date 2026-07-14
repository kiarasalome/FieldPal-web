package com.fieldPal.model.enums;

public enum Zone {
    NORTE("Zona Norte", "🏔️", "north"),
    SUR("Zona Sur", "🌄", "south"),
    ESTE("Zona Este", "🌅", "east"),
    OESTE("Zona Oeste", "🌇", "west"),
    CENTRO("Centro de Loja", "🏟️", "location_city");

    private final String label;
    private final String emoji;
    private final String icon;

    Zone(String label, String emoji, String icon) {
        this.label = label;
        this.emoji = emoji;
        this.icon = icon;
    }

    public String getLabel() { return label; }
    public String getEmoji() { return emoji; }
    public String getIcon() { return icon; }
}
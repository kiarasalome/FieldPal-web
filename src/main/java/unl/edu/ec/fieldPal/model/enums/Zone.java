package unl.edu.ec.fieldPal.model.enums;

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

    /**
     * Helper para parsear de forma segura desde cadenas recibidas en
     * componentes JSF, selects o APIs, evitando IllegalArgumentException.
     */
    public static Zone fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (Zone zone : Zone.values()) {
            if (zone.name().equalsIgnoreCase(value) || zone.getLabel().equalsIgnoreCase(value)) {
                return zone;
            }
        }
        return null;
    }
}
package unl.edu.ec.fieldPal.model.enums;

public enum CourtType {
    FUTBOL("Fútbol", "sports_soccer", "⚽"),
    VOLEY("Vóley", "sports_volleyball", "🏐"),
    TENNIS("Tenis", "sports_tennis", "🎾"),
    PADEL("Pádel", "sports_tennis", "🏓");

    private final String label;
    private final String icon;
    private final String emoji;

    CourtType(String label, String icon, String emoji) {
        this.label = label;
        this.icon = icon;
        this.emoji = emoji;
    }

    public String getLabel() { return label; }
    public String getIcon() { return icon; }
    public String getEmoji() { return emoji; }
}

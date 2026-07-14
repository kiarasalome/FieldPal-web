package com.fieldPal.model.enums;

public enum ReservationStatus {
    UPCOMING("Próxima", "event_upcoming", "chip-primary"),
    COMPLETED("Completada", "check_circle", "chip-outline"),
    CANCELLED("Cancelada", "cancel", "chip-outline");

    private final String label;
    private final String icon;
    private final String cssClass;

    ReservationStatus(String label, String icon, String cssClass) {
        this.label = label;
        this.icon = icon;
        this.cssClass = cssClass;
    }

    public String getLabel() { return label; }
    public String getIcon() { return icon; }
    public String getCssClass() { return cssClass; }
}


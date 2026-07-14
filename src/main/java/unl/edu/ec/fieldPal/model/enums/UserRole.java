package com.fieldPal.model.enums;


public enum UserRole {
    PLAYER("Jugador"),
    ADMIN("Administrador");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
package unl.edu.ec.fieldPal.model.enums;

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

    /**
     * Helper para parsear de forma segura desde cadenas recibidas en
     * componentes JSF, selects o APIs, evitando IllegalArgumentException.
     */
    public static UserRole fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(value) || role.getLabel().equalsIgnoreCase(value)) {
                return role;
            }
        }
        return null;
    }
}
package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import unl.edu.ec.fieldPal.model.enums.CourtType;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "courts")
public class Court implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "org_id", nullable = false)
    private String orgId; // FK lógica a organizations.id (sin relación @ManyToOne para no romper los beans/xhtml existentes, que usan orgId como String plano)

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private CourtType type;

    @Column(name = "price_per_hour", nullable = false)
    private double pricePerHour;

    @Column(name = "has_lighting")
    private boolean hasLighting;

    @Column(name = "covered")
    private boolean covered; // Cambiado a covered para coincidir con la convención de JSF y PrimeFaces

    @Column(name = "surface", length = 100)
    private String surface;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    public Court() {}

    public Court(String id, String orgId, String name, CourtType type, double pricePerHour,
                 boolean hasLighting, boolean covered, String surface, String imageUrl) {
        this.id = id;
        this.orgId = orgId;
        this.name = name;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.hasLighting = hasLighting;
        this.covered = covered;
        this.surface = surface;
        this.imageUrl = imageUrl;
    }

    // === Métodos Puente de Compatibilidad con WizardBean ===

    /**
     * Getter compatible con organizationId requerido por WizardBean.
     */
    public String getOrganizationId() {
        return this.orgId;
    }

    /**
     * Setter compatible con organizationId requerido por WizardBean.
     */
    public void setOrganizationId(String organizationId) {
        this.orgId = organizationId;
    }

    // === Getters and Setters Estándar ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrgId() { return orgId; }
    public void setOrgId(String orgId) { this.orgId = orgId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CourtType getType() { return type; }
    public void setType(CourtType type) { this.type = type; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    // Corregido: getter estándar para booleanos
    public boolean isHasLighting() { return hasLighting; }
    public void setHasLighting(boolean hasLighting) { this.hasLighting = hasLighting; }

    // Corregidos para evitar colisiones en la evaluación de expresiones JSF (#{cancha.covered})
    public boolean isCovered() { return covered; }
    public void setCovered(boolean covered) { this.covered = covered; }

    public String getSurface() { return surface; }
    public void setSurface(String surface) { this.surface = surface; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

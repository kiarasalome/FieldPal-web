package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.fieldPal.model.enums.CourtType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "courts")
public class Court implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, length = 50)
    private Long id;

    // Relación ManyToOne con PostgreSQL (Foreign Key: org_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false)
    private Organization organization;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Ingrese un nombre de identificación para la cancha")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    @NotNull(message = "Asigne un deporte a la cancha")
    private CourtType type;

    @Column(name = "price_per_hour", nullable = false)
    @Min(value = 0, message = "El precio debe ser un valor positivo")
    private double pricePerHour;

    @Column(name = "has_lighting")
    private boolean hasLighting;

    @Column(name = "covered")
    private boolean covered;

    @Column(name = "surface", length = 100)
    private String surface;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    public Court() {}

    public Court(Long id, Organization organization, String name, CourtType type,
                 double pricePerHour, boolean hasLighting, boolean covered,
                 String surface, String imageUrl) {
        this.id = id;
        this.organization = organization;
        this.name = name;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.hasLighting = hasLighting;
        this.covered = covered;
        this.surface = surface;
        this.imageUrl = imageUrl;
    }

    // === Métodos de Compatibilidad con Tu Código Actual (Getter/Setter de orgId) ===

    public String getOrgId() {
        return organization != null ? organization.getId() : null;
    }

    public void setOrgId(String orgId) {
        // Mantiene compatibilidad si pasas un String ID directamente
        if (this.organization == null) {
            this.organization = new Organization();
        }
        this.organization.setId(orgId);
    }

    public String getOrganizationId() {
        return getOrgId();
    }

    public void setOrganizationId(String organizationId) {
        setOrgId(organizationId);
    }

    // === Getters y Setters Estándar ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CourtType getType() { return type; }
    public void setType(CourtType type) { this.type = type; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public boolean isHasLighting() { return hasLighting; }
    public void setHasLighting(boolean hasLighting) { this.hasLighting = hasLighting; }

    public boolean isCovered() { return covered; }
    public void setCovered(boolean covered) { this.covered = covered; }

    public String getSurface() { return surface; }
    public void setSurface(String surface) { this.surface = surface; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Court)) return false;
        Court court = (Court) o;
        return Objects.equals(id, court.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
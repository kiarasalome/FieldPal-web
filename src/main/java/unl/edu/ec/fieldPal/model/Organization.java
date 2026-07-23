package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.fieldPal.model.enums.Zone;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "organizations")
public class Organization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, length = 50)
    @NotBlank(message = "El RUC o Identificador de la organización es obligatorio")
    private String id;

    @Column(name = "name", nullable = false, length = 150)
    @NotBlank(message = "Ingrese el nombre del complejo deportivo")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "zone", nullable = false, length = 50)
    @NotNull(message = "Debe seleccionar una zona de la ciudad")
    private Zone zone;

    @Column(name = "address", nullable = false, length = 255)
    @NotBlank(message = "La dirección del complejo es requerida")
    private String address;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "email", length = 100)
    @Email(message = "Ingrese una dirección de correo electrónico válida")
    private String email;

    @Column(name = "image", length = 500)
    private String image;

    @Column(name = "rating")
    @Min(value = 0, message = "La calificación no puede ser negativa")
    private double rating;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "court_count")
    @Min(value = 0, message = "El número de canchas no puede ser negativo")
    private int courtCount;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    // Relación OneToMany: Mapeada por el atributo 'organization' en la clase Court
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Court> courts = new ArrayList<>();

    public Organization() {}

    public Organization(String id, String name, Zone zone, String address, String phone,
                        String email, String image, double rating, String description,
                        int courtCount, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.rating = rating;
        this.description = description;
        this.courtCount = courtCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Método helper para agregar canchas de forma segura
    public void addCourt(Court court) {
        courts.add(court);
        court.setOrganization(this);
        this.courtCount = courts.size();
    }

    public void removeCourt(Court court) {
        courts.remove(court);
        court.setOrganization(null);
        this.courtCount = courts.size();
    }

    // === Getters y Setters ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCourtCount() { return courtCount; }
    public void setCourtCount(int courtCount) { this.courtCount = courtCount; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public List<Court> getCourts() { return courts; }
    public void setCourts(List<Court> courts) { this.courts = courts; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
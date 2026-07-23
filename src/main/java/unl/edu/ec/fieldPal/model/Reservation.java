package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "user_id", length = 50)
    private String userId;

    // Relación con Organización (FK: org_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false)
    private Organization organization;

    // Relación con Cancha (FK: court_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", referencedColumnName = "id", nullable = false)
    private Court court;

    @Column(name = "reservation_date", nullable = false)
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate date;

    @Column(name = "reservation_hour", nullable = false)
    @NotNull(message = "La hora de reserva es obligatoria")
    private LocalTime hour;

    @Column(name = "duration", nullable = false)
    @Positive(message = "La duración debe ser mayor a cero")
    private int duration;

    @Column(name = "player_count")
    @Min(value = 1, message = "Debe haber al menos 1 jugador")
    private int playerCount;

    @Column(name = "total_price", nullable = false)
    @Min(value = 0, message = "El precio total no puede ser negativo")
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @NotNull(message = "Defina el estado de la reserva")
    private ReservationStatus status;

    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;

    @Column(name = "contact_name", length = 150)
    @NotBlank(message = "El nombre de contacto es obligatorio")
    private String contactName;

    @Column(name = "contact_phone", length = 30)
    private String contactPhone;

    public Reservation() {}

    public Reservation(String id, String userId, Organization organization, Court court,
                       LocalDate date, LocalTime hour, int duration, int playerCount,
                       double totalPrice, ReservationStatus status, boolean confirmed,
                       String contactName, String contactPhone) {
        this.id = id;
        this.userId = userId;
        this.organization = organization;
        this.court = court;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.playerCount = playerCount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.confirmed = confirmed;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    // === Métodos Puente de Compatibilidad con IDs (String) ===

    public String getOrgId() {
        return organization != null ? organization.getId() : null;
    }

    public void setOrgId(String orgId) {
        if (this.organization == null) {
            this.organization = new Organization();
        }
        this.organization.setId(orgId);
    }

    public String getCourtId() {
        return court != null ? court.getId() : null;
    }

    public void setCourtId(String courtId) {
        if (this.court == null) {
            this.court = new Court();
        }
        this.court.setId(courtId);
    }

    // === Getters y Setters Estándar ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }

    public Court getCourt() { return court; }
    public void setCourt(Court court) { this.court = court; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHour() { return hour; }
    public void setHour(LocalTime hour) { this.hour = hour; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getPlayerCount() { return playerCount; }
    public void setPlayerCount(int playerCount) { this.playerCount = playerCount; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    // === Equals & HashCode ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
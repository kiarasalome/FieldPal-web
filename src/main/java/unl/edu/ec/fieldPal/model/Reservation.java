package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;

import java.io.Serializable;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @Column(name = "court_id", nullable = false)
    private String courtId;

    @NotNull @NotEmpty
    @Column(name = "reservation_date", nullable = false, length = 20)
    private String date;

    @NotNull @NotEmpty
    @Column(name = "hour", nullable = false, length = 10)
    private String hour;

    @Column(name = "duration")
    private int duration;

    @Column(name = "player_count")
    private int playerCount;

    @Column(name = "total_price")
    private double totalPrice;

    // Nota: @NotEmpty solo aplica a String/Collection; se quitó de status y
    // confirmed porque en un enum/boolean provocaba un error de validación
    // inválido (ConstraintDeclarationException) que nunca se había disparado
    // porque nada llamaba a un Validator sobre este bean todavía.
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status;

    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "contact_name", length = 150)
    private String contactName;

    @Column(name = "contact_phone", length = 30)
    private String contactPhone;

    public Reservation() {}

    public Reservation(String id, String userId, String orgId, String courtId, String date,
                       String hour, int duration, int playerCount, double totalPrice,
                       ReservationStatus status, boolean confirmed, String contactName,
                       String contactPhone) {
        this.id = id;
        this.userId = userId;
        this.orgId = orgId;
        this.courtId = courtId;
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

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOrgId() { return orgId; }
    public void setOrgId(String orgId) { this.orgId = orgId; }

    public String getCourtId() { return courtId; }
    public void setCourtId(String courtId) { this.courtId = courtId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

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
}

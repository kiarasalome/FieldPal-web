package unl.edu.ec.fieldPal.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import java.io.Serializable;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String orgId;
    private String courtId;

    @NotNull @NotEmpty
    private String date;

    @NotNull @NotEmpty
    private String hour;
    private int duration;
    private int playerCount;
    private double totalPrice;

    @NotNull @NotEmpty
    private ReservationStatus status;

    @NotNull @NotEmpty
    private boolean confirmed;
    private String contactName;
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


package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "time_slots")
public class TimeSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    // Id técnico autogenerado: TimeSlot no tenía id propio (se identificaba por
    // hour+courtId+date), pero JPA necesita una clave primaria por tabla.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "hour", nullable = false, length = 10)
    private String hour;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Column(name = "court_id", nullable = false)
    private String courtId;

    @Column(name = "slot_date", nullable = false, length = 20)
    private String date;

    public TimeSlot() {}

    public TimeSlot(String hour, boolean available, String courtId, String date) {
        this.hour = hour;
        this.available = available;
        this.courtId = courtId;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getCourtId() { return courtId; }
    public void setCourtId(String courtId) { this.courtId = courtId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

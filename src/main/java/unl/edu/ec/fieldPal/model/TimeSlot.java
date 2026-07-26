package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "time_slots")
public class TimeSlot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "La cancha es obligatoria")
    private Court court;

    @Column(name = "slot_date", nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate date;

    @Column(name = "slot_hour", nullable = false)
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hour;

    @Column(name = "available", nullable = false)
    private boolean available;

    public TimeSlot() {}

    public TimeSlot(Long id, Court court, LocalDate date, LocalTime hour, boolean available) {
        this.id = id;
        this.court = court;
        this.date = date;
        this.hour = hour;
        this.available = available;
    }

    public TimeSlot(LocalTime hour, boolean b, Long courtId, LocalDate date) {
    }

    // === Método Puente para compatibilidad con String courtId ===

    public Long getCourtId() {
        return court != null ? court.getId() : null;
    }

    public void setCourtId(Long courtId) {
        if (this.court == null) {
            this.court = new Court();
        }
        this.court.setId(courtId);
    }

    // === Getters y Setters ===

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Court getCourt() { return court; }
    public void setCourt(Court court) { this.court = court; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHour() { return hour; }
    public void setHour(LocalTime hour) { this.hour = hour; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(id, timeSlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
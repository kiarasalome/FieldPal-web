package com.fieldPal.model;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    private static final long serialVersionUID = 1L;

    private String hour;
    private boolean available;
    private String courtId;
    private String date;

    public TimeSlot() {}

    public TimeSlot(String hour, boolean available, String courtId, String date) {
        this.hour = hour;
        this.available = available;
        this.courtId = courtId;
        this.date = date;
    }

    // Getters and Setters
    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getCourtId() { return courtId; }
    public void setCourtId(String courtId) { this.courtId = courtId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

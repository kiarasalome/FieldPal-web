package unl.edu.ec.fieldPal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ScheduleService {
    private final List<TimeSlot> schedule = new ArrayList<>();

    public ScheduleService() {
        schedule.add(new TimeSlot("08:00", false, "c1", "2026-07-18"));
        schedule.add(new TimeSlot("10:00", false, "c2", "2026-07-18"));
        schedule.add(new TimeSlot("12:00", false, "c3", "2026-07-18"));
        schedule.add(new TimeSlot("14:00", false, "c4", "2026-07-19"));
        schedule.add(new TimeSlot("16:00", false, "c5", "2026-07-19"));
        schedule.add(new TimeSlot("18:00", false, "c6", "2026-07-19"));
        schedule.add(new TimeSlot("20:00", false, "c7", "2026-07-19"));
        schedule.add(new TimeSlot("22:00", false, "c8", "2026-07-20"));
        schedule.add(new TimeSlot("08:00", false, "c9", "2026-07-20"));
        schedule.add(new TimeSlot("10:00", false, "c10", "2026-07-20"));
        schedule.add(new TimeSlot("12:00", false, "c11", "2026-07-20"));
        schedule.add(new TimeSlot("14:00", false, "c12", "2026-07-20"));
        schedule.add(new TimeSlot("16:00", false, "c13", "2026-07-20"));
        schedule.add(new TimeSlot("18:00", false, "c14", "2026-07-21"));
        schedule.add(new TimeSlot("20:00", false, "c15", "2026-07-21"));
        schedule.add(new TimeSlot("22:00", false, "c16", "2026-07-21"));
        schedule.add(new TimeSlot("08:00", false, "c17", "2026-07-21"));
        schedule.add(new TimeSlot("10:00", false, "c18", "2026-07-22"));
        schedule.add(new TimeSlot("12:00", false, "c19", "2026-07-22"));
        schedule.add(new TimeSlot("14:00", false, "c20", "2026-07-22"));
        schedule.add(new TimeSlot("16:00", false, "c21", "2026-07-22"));
        schedule.add(new TimeSlot("18:00", false, "c22", "2026-07-22"));
        schedule.add(new TimeSlot("20:00", false, "c23", "2026-07-22"));
        schedule.add(new TimeSlot("22:00", false, "c24", "2026-07-22"));
    }

    public List<TimeSlot> getSchedule(String courtId, String date) {
        List<TimeSlot> slots = new ArrayList<>();
        for (int h = 8; h <= 22; h++) {
            String hour = String.format("%02d:00", h);
            boolean reserved = this.schedule.stream()
                    .anyMatch(slot -> slot.getHour().equals(hour)
                            && slot.getCourtId().equals(courtId)
                            && slot.getDate().equals(date));
            slots.add(new TimeSlot(hour, !reserved, courtId, date));
        }
        return slots;
    }

    public void reserve(String courtId, String date, String hour) {
        schedule.add(new TimeSlot(hour, false, courtId, date));
    }
}

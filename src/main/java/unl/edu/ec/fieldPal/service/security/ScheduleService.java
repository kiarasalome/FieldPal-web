package unl.edu.ec.fieldPal.service.security;

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

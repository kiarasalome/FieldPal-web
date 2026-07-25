package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class ScheduleService {

    private CrudGenericService crudService = new CrudGenericService();

    public ScheduleService() {
    }

    public List<TimeSlot> getSchedule(String courtId, String date) {
        String jpql = "SELECT r.hour FROM Reservation r WHERE r.courtId = :courtId AND r.date = :date";
        Map<String, Object> params = new HashMap<>();
        params.put("courtId", courtId);
        params.put("date", date);

        List<String> reservedHours = crudService.findWithQuery(jpql, params);

        List<TimeSlot> slots = new ArrayList<>();
        for (int h = 8; h <= 22; h++) {
            String hour = String.format("%02d:00", h);
            boolean reserved = reservedHours.contains(hour);
            slots.add(new TimeSlot(hour, !reserved, courtId, date));
        }
        return slots;
    }

    public TimeSlot reserve(String courtId, String date, String hour) {
        TimeSlot slot = new TimeSlot(hour, false, courtId, date);
        return crudService.create(slot);
    }
}

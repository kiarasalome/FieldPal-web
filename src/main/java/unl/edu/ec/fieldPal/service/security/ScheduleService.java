package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NeoCoreTeam
 */

@Named
@ApplicationScoped
public class ScheduleService {

    @Inject
    private CrudGenericService crudService;


    public ScheduleService() {
    }

    public List<TimeSlot> getSchedule(Long courtId, LocalDate date) {
        String jpql = "SELECT r.hour FROM Reservation r WHERE r.courtId = :courtId AND r.date = :date";
        Map<String, Object> params = new HashMap<>();
        params.put("courtId", courtId);
        params.put("date", date);

        List<LocalTime> reservedHours = crudService.findWithQuery(jpql, params);

        List<TimeSlot> slots = new ArrayList<>();
        for (int h = 8; h <= 22; h++) {
            LocalTime hour = LocalTime.of(h, 0);
            boolean reserved = reservedHours.contains(hour);
            slots.add(new TimeSlot());
        }
        return slots;
    }

    public void reserve() {
        TimeSlot slot = new TimeSlot();
        crudService.create(slot);
    }
}

package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class ScheduleService {

    private static final int OPENING_HOUR = 8;
    private static final int CLOSING_HOUR = 22;

    @Inject
    private CrudGenericService crudService;

    public ScheduleService() {
    }

    public List<TimeSlot> getSchedule(Long courtId, LocalDate date) {
        if (courtId == null || date == null) return new ArrayList<>();
        String jpql = "SELECT r.hour FROM Reservation r " +
                "WHERE r.courtId = :courtId AND r.date = :date AND r.status != :status";
        Map<String, Object> params = new HashMap<>();
        params.put("courtId", courtId);
        params.put("date", date);
        params.put("status", ReservationStatus.CANCELLED);

        List<LocalTime> reservedHours = crudService.findWithQuery(jpql, params);

        List<TimeSlot> slots = new ArrayList<>();
        for (int h = OPENING_HOUR; h <= CLOSING_HOUR; h++) {
            LocalTime hour = LocalTime.of(h, 0);
            boolean reserved = reservedHours.contains(hour);
            slots.add(new TimeSlot(hour, !reserved, courtId, date));
        }
        return slots;
    }

    public TimeSlot reserve(Long courtId, LocalDate date, LocalTime hour) {
        if (courtId == null || date == null || hour == null) return null;
        TimeSlot slot = new TimeSlot(hour, false, courtId, date);
        return crudService.create(slot);
    }
}
package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.service.repository.CourtRepository;
import unl.edu.ec.fieldPal.service.repository.TimeSlotRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ScheduleService {

    @Inject
    private TimeSlotRepository timeSlotRepository;

    @Inject
    private CourtRepository courtRepository;

    public ScheduleService() {
    }

    public List<TimeSlot> getSchedule(Long courtId, LocalDate date) {
        if (courtId == null || date == null) return new ArrayList<>();

        Court court = courtRepository.findById(courtId);
        List<LocalTime> reservedHours = timeSlotRepository.findReservedHours(courtId, date);

        List<TimeSlot> slots = new ArrayList<>();
        for (int h = 8; h <= 22; h++) {
            LocalTime time = LocalTime.of(h, 0);
            boolean isReserved = reservedHours.contains(time);
            slots.add(new TimeSlot(court, date, time, !isReserved));
        }
        return slots;
    }

    public List<TimeSlot> getSchedule(String courtIdStr, String dateStr) {
        if (courtIdStr == null || courtIdStr.isBlank() || dateStr == null || dateStr.isBlank()) {
            return new ArrayList<>();
        }
        try {
            Long courtId = Long.valueOf(courtIdStr);
            LocalDate date = LocalDate.parse(dateStr);
            return getSchedule(courtId, date);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public TimeSlot reserve(Long courtId, LocalDate date, LocalTime hour) {
        Court court = courtRepository.findById(courtId);
        TimeSlot slot = new TimeSlot(court, date, hour, false);
        return timeSlotRepository.save(slot);
    }

    public TimeSlot reserve(String courtIdStr, String dateStr, String hourStr) {
        try {
            Long courtId = Long.valueOf(courtIdStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime hour = LocalTime.parse(hourStr);
            return reserve(courtId, date, hour);
        } catch (Exception e) {
            return null;
        }
    }
}

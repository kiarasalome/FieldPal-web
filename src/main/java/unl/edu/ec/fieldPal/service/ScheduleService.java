package unl.edu.ec.fieldPal.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.service.repository.TimeSlotRepository;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ScheduleService {

    @Inject
    private TimeSlotRepository timeSlotRepository;

    // Nota: en esta tabla solo se guarda una fila por cada hora YA RESERVADA
    // (igual que en la versión original en memoria). Si una hora no aparece
    // aquí para esa cancha+fecha, se considera disponible.
    @PostConstruct
    @Transactional
    public void seedIfEmpty() {
        if (timeSlotRepository.count() > 0) return;

        timeSlotRepository.save(new TimeSlot("08:00", false, "c1", "2026-07-18"));
        timeSlotRepository.save(new TimeSlot("10:00", false, "c2", "2026-07-18"));
        timeSlotRepository.save(new TimeSlot("12:00", false, "c3", "2026-07-18"));
        timeSlotRepository.save(new TimeSlot("14:00", false, "c4", "2026-07-19"));
        timeSlotRepository.save(new TimeSlot("16:00", false, "c5", "2026-07-19"));
        timeSlotRepository.save(new TimeSlot("18:00", false, "c6", "2026-07-19"));
        timeSlotRepository.save(new TimeSlot("20:00", false, "c7", "2026-07-19"));
        timeSlotRepository.save(new TimeSlot("22:00", false, "c8", "2026-07-20"));
        timeSlotRepository.save(new TimeSlot("08:00", false, "c9", "2026-07-20"));
        timeSlotRepository.save(new TimeSlot("10:00", false, "c10", "2026-07-20"));
        timeSlotRepository.save(new TimeSlot("12:00", false, "c11", "2026-07-20"));
        timeSlotRepository.save(new TimeSlot("14:00", false, "c12", "2026-07-20"));
        timeSlotRepository.save(new TimeSlot("16:00", false, "c13", "2026-07-20"));
        timeSlotRepository.save(new TimeSlot("18:00", false, "c14", "2026-07-21"));
        timeSlotRepository.save(new TimeSlot("20:00", false, "c15", "2026-07-21"));
        timeSlotRepository.save(new TimeSlot("22:00", false, "c16", "2026-07-21"));
        timeSlotRepository.save(new TimeSlot("08:00", false, "c17", "2026-07-21"));
        timeSlotRepository.save(new TimeSlot("10:00", false, "c18", "2026-07-22"));
        timeSlotRepository.save(new TimeSlot("12:00", false, "c19", "2026-07-22"));
        timeSlotRepository.save(new TimeSlot("14:00", false, "c20", "2026-07-22"));
        timeSlotRepository.save(new TimeSlot("16:00", false, "c21", "2026-07-22"));
        timeSlotRepository.save(new TimeSlot("18:00", false, "c22", "2026-07-22"));
        timeSlotRepository.save(new TimeSlot("20:00", false, "c23", "2026-07-22"));
        timeSlotRepository.save(new TimeSlot("22:00", false, "c24", "2026-07-22"));
    }

    public List<TimeSlot> getSchedule(String courtId, String date) {
        List<String> reservedHours = timeSlotRepository.findReservedHours(courtId, date);

        List<TimeSlot> slots = new ArrayList<>();
        for (int h = 8; h <= 22; h++) {
            String hour = String.format("%02d:00", h);
            boolean reserved = reservedHours.contains(hour);
            slots.add(new TimeSlot(hour, !reserved, courtId, date));
        }
        return slots;
    }

    @Transactional
    public void reserve(String courtId, String date, String hour) {
        timeSlotRepository.save(new TimeSlot(hour, false, courtId, date));
    }
}

package unl.edu.ec.fieldPal.service.repository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TimeSlotRepository {

    @Inject
    private CrudGenericService crud;

    public TimeSlot save(TimeSlot slot) {
        return crud.create(slot);
    }

    public List<String> findReservedHours(String courtId, String date) {
        Map<String, Object> params = new HashMap<>();
        params.put("courtId", courtId);
        params.put("date", date);
        return crud.findWithQuery(
                "SELECT t.hour FROM TimeSlot t WHERE t.courtId = :courtId AND t.date = :date",
                String.class, params);
    }

    public long count() {
        return crud.count("SELECT COUNT(t) FROM TimeSlot t");
    }
}

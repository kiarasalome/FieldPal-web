package unl.edu.ec.fieldPal.service.repository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ReservationRepository {

    @Inject
    private CrudGenericService crud;

    public Reservation save(Reservation reservation) {
        if (crud.find(Reservation.class, reservation.getId()) == null) {
            return crud.create(reservation);
        }
        return crud.update(reservation);
    }

    public Reservation findById(String id) {
        if (id == null) return null;
        return crud.find(Reservation.class, id);
    }

    public List<Reservation> findAll() {
        return crud.findWithQuery("SELECT r FROM Reservation r", Reservation.class);
    }

    public List<Reservation> findByUser(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return crud.findWithQuery("SELECT r FROM Reservation r WHERE r.userId = :userId", Reservation.class, params);
    }

    public long countByStatus(ReservationStatus status) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        List<Reservation> results = crud.findWithQuery(
                "SELECT r FROM Reservation r WHERE r.status = :status", Reservation.class, params);
        return results.size();
    }

    public double sumTotalPriceExcluding(ReservationStatus excludedStatus) {
        return findAll().stream()
                .filter(r -> r.getStatus() != excludedStatus)
                .mapToDouble(Reservation::getTotalPrice)
                .sum();
    }
}

package unl.edu.ec.fieldPal.service.security;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.*;

@Named
@ApplicationScoped
public class ReservationService {

    @Inject
    private CrudGenericService crudService;

    public ReservationService() {
    }

    public List<Reservation> getAll() {
        return crudService.findWithQuery("SELECT r FROM Reservation r", Collections.emptyMap());
    }

    public List<Reservation> getByUser(Long userId) {
        if (userId == null) return new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("user", userId);
        return crudService.findWithNamedQuery("Reservation.getByUser", params);
    }

    public Reservation findById(Long id) {
        if (id == null) return null;
        Reservation reservation = crudService.find(Reservation.class, id);
        if (reservation == null) {
            throw new EntityNotFoundException("Reserva no encontrada con [" + id + "]");
        }
        return reservation;
    }

    public void addReservation(Reservation res) {
        if (res == null) return;
        crudService.create(res);
    }

    public void cancelReservation(Long id) {
        Reservation res = findById(id);
        // findById lanza EntityNotFoundException si no existe, así que aquí
        // res siempre está garantizado como no-null.
        res.setStatus(ReservationStatus.CANCELLED);
        crudService.update(res);
    }

    public void confirmReservation(Long id) {
        Reservation res = findById(id);
        res.setConfirmed(true);
        crudService.update(res);
    }

    public void updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() == null) return;
        crudService.update(reservation);
    }

    public int getActiveCount() {
        String jpql = "SELECT COUNT(r) FROM Reservation r WHERE r.status = :status";
        Map<String, Object> params = new HashMap<>();
        params.put("status", ReservationStatus.UPCOMING);

        List<Long> result = crudService.findWithQuery(jpql, params);
        return result.isEmpty() ? 0 : result.get(0).intValue();
    }

    public double getMonthlyIncome() {
        String jpql = "SELECT SUM(r.totalPrice) FROM Reservation r WHERE r.status != :status";
        Map<String, Object> params = new HashMap<>();
        params.put("status", ReservationStatus.CANCELLED);

        List<Double> result = crudService.findWithQuery(jpql, params);
        if (result.isEmpty() || result.get(0) == null) return 0.0;
        return result.get(0);
    }
}
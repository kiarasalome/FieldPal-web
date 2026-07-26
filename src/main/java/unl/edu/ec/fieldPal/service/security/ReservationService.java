package unl.edu.ec.fieldPal.service.security;


import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.*;

/**
 * @author NeoCoreTeam
 */

@Named
@ApplicationScoped
public class ReservationService {

    @Inject
    private CrudGenericService crudService;

    public ReservationService() {
    }

    public List<Reservation> getAll() {
        String jpql = "SELECT DISTINCT o.reservations FROM Organization o";
        return crudService.findWithQuery(jpql, Collections.emptyMap());
    }

    public List<Reservation> getByUser(Long userId){
        Map<String, Object> params = new HashMap<>();
        params.put("user", userId);
        return crudService.findWithNamedQuery("Reservation.getByUser", params);
    }

    public Reservation findById(Long id) throws EntityNotFoundException {
        Reservation reservation = crudService.find(Reservation.class, id);
        if (reservation == null){
            throw new EntityNotFoundException("Organization no encontrada con [" + id + "]");
        }
        return reservation;
    }

    public void addReservation(Reservation res) {
        if (res == null) return;
        crudService.create(res);

    }

    public void cancelReservation(Long id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setStatus(ReservationStatus.CANCELLED);
        }
    }

    public void confirmReservation(Long id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setConfirmed(true);
        }
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
        return result.get(0).intValue();
    }

    public double getMonthlyIncome() {
        String jpql = "SELECT SUM(r.totalPrice) FROM Reservation r WHERE r.status != :status";
        Map<String, Object> params = new HashMap<>();
        params.put("status", ReservationStatus.CANCELLED);

        List<Double> result = crudService.findWithQuery(jpql, params);
        Double sum = result.get(0);
        return sum != null ? sum : 0.0;
    }

}

package unl.edu.ec.fieldPal.service.security;


import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationService() {

    }

    public List<Reservation> getAll() {
        return new ArrayList<>(reservations);
    }

    public List<Reservation> getByUser(String userId) {
        return reservations.stream()
                .filter(r -> r.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Reservation findById(String id) {
        return reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void addReservation(Reservation res) {
        String id = "r" + (reservations.size() + 1);
        res.setId(id);
        reservations.add(res);
    }

    public void cancelReservation(String id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setStatus(ReservationStatus.CANCELLED);
        }
    }

    public void confirmReservation(String id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setConfirmed(true);
        }
    }

    public void updateReservation(Reservation reservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId().equals(reservation.getId())) {
                reservations.set(i, reservation);
                return;
            }
        }
    }

    public int getActiveCount() {
        return (int) reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.UPCOMING)
                .count();
    }

    public double getMonthlyIncome() {
        return reservations.stream()
                .filter(r -> r.getStatus() != ReservationStatus.CANCELLED)
                .mapToDouble(Reservation::getTotalPrice)
                .sum();
    }

}

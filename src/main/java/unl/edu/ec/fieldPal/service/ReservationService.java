package unl.edu.ec.fieldPal.service;


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
        // Datos quemados - editar después para conectar a BD
        reservations.add(new Reservation("r1", "2", "o1", "c1", "2026-07-14",
                "19:00", 2, 10, 50, ReservationStatus.UPCOMING, true,
                "Carlos Mendoza", "+593 99 123 4567"));
        reservations.add(new Reservation("r2", "2", "o5", "c16", "2026-07-12",
                "09:00", 1, 5, 28, ReservationStatus.COMPLETED, true,
                "Carlos Mendoza", "+593 99 123 4567"));
        reservations.add(new Reservation("r3", "2", "o3", "c8", "2026-07-10",
                "15:00", 1, 2, 20, ReservationStatus.COMPLETED, true,
                "Carlos Mendoza", "+593 99 123 4567"));
        reservations.add(new Reservation("r4", "2", "o2", "c6", "2026-07-08",
                "17:00", 2, 6, 28, ReservationStatus.CANCELLED, false,
                "Carlos Mendoza", "+593 99 123 4567"));
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

    public int getReservationCount() {
        return reservations.size();
    }
}

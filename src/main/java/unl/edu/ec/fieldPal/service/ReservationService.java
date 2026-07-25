package unl.edu.ec.fieldPal.service;

import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import unl.edu.ec.fieldPal.service.repository.ReservationRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Named
@ApplicationScoped
public class ReservationService {

    @Inject
    private ReservationRepository reservationRepository;

    @PostConstruct
    @Transactional
    public void seedIfEmpty() {
        if (!reservationRepository.findAll().isEmpty()) return;

        reservationRepository.save(new Reservation("r1", "2", "o1", "c1", "2026-07-14",
                "19:00", 2, 10, 50, ReservationStatus.UPCOMING, true,
                "Carlos Mendoza", "+593 99 123 4567"));
        reservationRepository.save(new Reservation("r2", "2", "o5", "c16", "2026-07-12",
                "09:00", 1, 5, 28, ReservationStatus.COMPLETED, true,
                "Carlos Mendoza", "+593 99 123 4567"));
        reservationRepository.save(new Reservation("r3", "2", "o3", "c8", "2026-07-10",
                "15:00", 1, 2, 20, ReservationStatus.COMPLETED, true,
                "Carlos Mendoza", "+593 99 123 4567"));
        reservationRepository.save(new Reservation("r4", "2", "o2", "c6", "2026-07-08",
                "17:00", 2, 6, 28, ReservationStatus.CANCELLED, false,
                "Carlos Mendoza", "+593 99 123 4567"));
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getByUser(String userId) {
        return reservationRepository.findByUser(userId);
    }

    public Reservation findById(String id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public void addReservation(Reservation res) {
        res.setId(UUID.randomUUID().toString());
        reservationRepository.save(res);
    }

    @Transactional
    public void cancelReservation(String id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(res);
        }
    }

    @Transactional
    public void confirmReservation(String id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setConfirmed(true);
            reservationRepository.save(res);
        }
    }

    @Transactional
    public void updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() == null) return;
        reservationRepository.save(reservation);
    }

    public int getActiveCount() {
        return (int) reservationRepository.countByStatus(ReservationStatus.UPCOMING);
    }

    public double getMonthlyIncome() {
        return reservationRepository.sumTotalPriceExcluding(ReservationStatus.CANCELLED);
    }
}

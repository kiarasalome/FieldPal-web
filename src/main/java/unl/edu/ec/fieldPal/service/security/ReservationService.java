package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import unl.edu.ec.fieldPal.service.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ReservationService {

    @Inject
    private ReservationRepository reservationRepository;

    public ReservationService() {
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getByUser(Long userId) {
        if (userId == null) return new ArrayList<>();
        return reservationRepository.findByUser(userId);
    }

    public Reservation findById(Long id) throws EntityNotFoundException {
        if (id == null) return null;
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new EntityNotFoundException("Reserva no encontrada con ID [" + id + "]");
        }
        return reservation;
    }

    public void addReservation(Reservation res) {
        if (res == null) return;
        reservationRepository.save(res);
    }

    public void addRervation(Reservation res) {
        addReservation(res);
    }

    public void cancelReservation(Long id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(res);
        }
    }

    public void confirmReservation(Long id) {
        Reservation res = findById(id);
        if (res != null) {
            res.setConfirmed(true);
            reservationRepository.save(res);
        }
    }

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

package unl.edu.ec.fieldPal.controller;

import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
import unl.edu.ec.fieldPal.model.enums.Zone;
import unl.edu.ec.fieldPal.service.CourtService;
import unl.edu.ec.fieldPal.service.OrganizationService;
import unl.edu.ec.fieldPal.service.ReservationService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para la página de reserva.
 * Datos quemados - editar después para conectar a BD real.
 */

@Named
@ViewScoped
public class ReservaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private CourtService courtService;

    @Inject
    private ReservationService reservationService;

    @Inject
    private AuthBean authBean;

    // Filtros
    private Zone selectedZone;
    private String selectedOrgId = "";
    private String selectedCourtId = "";

    // Datos de reserva
    private String date = "";
    private String hour = "";
    private int duration = 1;
    private int playerCount = 5;
    private String contactName = "";
    private String contactPhone = "";

    // Estado
    private boolean submitted = false;

    public List<Organization> getFilteredOrgs() {
        if (selectedZone != null) {
            return organizationService.getByZone(selectedZone);
        }
        return organizationService.getAll();
    }

    public List<Court> getFilteredCourts() {
        if (selectedOrgId != null && !selectedOrgId.isEmpty()) {
            return courtService.getByOrg(selectedOrgId);
        }
        return courtService.getAll();
    }

    public Court getActiveCourt() {
        if (selectedCourtId != null && !selectedCourtId.isEmpty()) {
            return courtService.findById(selectedCourtId);
        }
        return null;
    }

    public double getTotalPrice() {
        Court court = getActiveCourt();
        if (court == null) return 0;
        return court.getPricePerHour() * duration;
    }

    public double getPricePerPlayer() {
        int players = Math.max(playerCount, 1);
        return getTotalPrice() / players;
    }

    public String[] getAvailableHours() {
        String[] hours = new String[15];
        for (int i = 0; i < 15; i++) {
            hours[i] = String.format("%02d:00", i + 8);
        }
        return hours;
    }

    public String doReserve() {
        // TODO: Implementar reserva real con BD y validación de sesión
        if (!authBean.isAuthenticated()) {
            authBean.openLogin();
            return null;
        }

        // Validaciones básicas
        if (selectedCourtId == null || selectedCourtId.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecciona una cancha.", ""));
            return null;
        }
        if (date == null || date.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecciona una fecha.", ""));
            return null;
        }
        if (hour == null || hour.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecciona una hora.", ""));
            return null;
        }

        Court court = getActiveCourt();
        Reservation res = new Reservation();
        res.setUserId(authBean.getCurrentUser().getId());
        res.setOrgId(court.getOrgId());
        res.setCourtId(selectedCourtId);
        res.setDate(date);
        res.setHour(hour);
        res.setDuration(duration);
        res.setPlayerCount(playerCount);
        res.setTotalPrice(getTotalPrice());
        res.setStatus(ReservationStatus.UPCOMING);
        res.setConfirmed(false);
        res.setContactName(contactName);
        res.setContactPhone(contactPhone);

        reservationService.addReservation(res);
        submitted = true;

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "¡Reserva confirmada! Cancha: " + court.getName() +
                                " | Fecha: " + date + " " + hour, ""));

        return "/mis-reservas.xhtml?faces-redirect=true";
    }

    // Getters y Setters
    public Zone getSelectedZone() { return selectedZone; }
    public void setSelectedZone(Zone selectedZone) { this.selectedZone = selectedZone; }

    public String getSelectedOrgId() { return selectedOrgId; }
    public void setSelectedOrgId(String selectedOrgId) { this.selectedOrgId = selectedOrgId; }

    public String getSelectedCourtId() { return selectedCourtId; }
    public void setSelectedCourtId(String selectedCourtId) { this.selectedCourtId = selectedCourtId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getPlayerCount() { return playerCount; }
    public void setPlayerCount(int playerCount) { this.playerCount = playerCount; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public boolean isSubmitted() { return submitted; }
    public void setSubmitted(boolean submitted) { this.submitted = submitted; }
}


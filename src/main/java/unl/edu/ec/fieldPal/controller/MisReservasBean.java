package unl.edu.ec.fieldPal.controller;

import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.Reservation;
import unl.edu.ec.fieldPal.model.enums.ReservationStatus;
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
import java.util.stream.Collectors;

/**
 * Managed Bean para la página de mis reservas.
 * Datos quemados - editar después para conectar a BD real.
 */
@Named
@ViewScoped
public class MisReservasBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ReservationService reservationService;

    @Inject
    private CourtService courtService;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private AuthBean authBean;

    // Filtros
    private String activeFilter = "all";
    private String search = "";

    // === Selección / edición ===
    private Reservation selectedReservation;
    private boolean showEditModal = false;

    public List<Reservation> getReservations() {
        if (!authBean.isAuthenticated()) return List.of();

        String userId = authBean.getCurrentUser().getId();
        List<Reservation> all = reservationService.getByUser(userId);

        return all.stream()
                .filter(r -> {
                    boolean matchesSearch = search.isEmpty()
                            || r.getId().toLowerCase().contains(search.toLowerCase())
                            || getCourtName(r.getCourtId()).toLowerCase().contains(search.toLowerCase())
                            || getOrgName(r.getOrgId()).toLowerCase().contains(search.toLowerCase());
                    if ("all".equals(activeFilter)) return matchesSearch;
                    return matchesSearch && r.getStatus().name().equalsIgnoreCase(activeFilter);
                })
                .collect(Collectors.toList());
    }

    public String getCourtName(String courtId) {
        Court court = courtService.findById(courtId);
        return court != null ? court.getName() : "—";
    }

    public String getOrgName(String orgId) {
        Organization org = organizationService.findById(orgId);
        return org != null ? org.getName() : "—";
    }

    /** Ícono Material Symbols asociado al tipo de cancha (para la tabla/modal). */
    public String getCourtIcon(String courtId) {
        Court court = courtService.findById(courtId);
        return court != null ? court.getType().getIcon() : "sports_soccer";
    }

    /** Mapea el estado de la reserva a las clases ya definidas en fieldpal.css. */
    public String getStatusClass(Reservation res) {
        if (res == null || res.getStatus() == null) return "";
        return switch (res.getStatus()) {
            case UPCOMING -> "fp-status-upcoming";
            case COMPLETED -> "fp-status-completed";
            case CANCELLED -> "fp-status-cancelled";
        };
    }

    public String formatDate(String dateStr) {
        // TODO: Formatear fecha con locale español
        return dateStr;
    }

    public void cancelReservation(String reservationId) {
        reservationService.cancelReservation(reservationId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva cancelada exitosamente.", ""));
    }

    public void confirmAttendance(String reservationId) {
        reservationService.confirmReservation(reservationId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Asistencia confirmada.", ""));
    }

    // === Modal de detalle / edición ===

    public void selectReservation(Reservation reservation) {
        this.selectedReservation = reservation;
        this.showEditModal = true;
    }

    public void closeEditModal() {
        this.showEditModal = false;
        this.selectedReservation = null;
    }

    /** Guarda los cambios hechos sobre la reserva seleccionada (solo aplica si está UPCOMING). */
    public void saveEditedReservation() {
        if (selectedReservation == null) return;
        reservationService.updateReservation(selectedReservation);
        showEditModal = false;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva actualizada correctamente.", ""));
    }

    /** Cancelar desde el modal: cancela y cierra en un solo paso. */
    public void cancelSelectedReservation() {
        if (selectedReservation != null) {
            cancelReservation(selectedReservation.getId());
        }
        closeEditModal();
    }

    /** Confirmar asistencia desde el modal: confirma y cierra en un solo paso. */
    public void confirmSelectedReservation() {
        if (selectedReservation != null) {
            confirmAttendance(selectedReservation.getId());
        }
        closeEditModal();
    }

    // Getters y Setters
    public String getActiveFilter() { return activeFilter; }
    public void setActiveFilter(String activeFilter) { this.activeFilter = activeFilter; }

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }

    public Reservation getSelectedReservation() { return selectedReservation; }
    public void setSelectedReservation(Reservation selectedReservation) { this.selectedReservation = selectedReservation; }

    public boolean isShowEditModal() { return showEditModal; }
    public void setShowEditModal(boolean showEditModal) { this.showEditModal = showEditModal; }
}
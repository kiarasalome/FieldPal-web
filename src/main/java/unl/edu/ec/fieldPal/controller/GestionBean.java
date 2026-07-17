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
import unl.edu.ec.fieldPal.service.UserService;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para el panel de administración (una sola página AJAX).
 * Datos quemados - editar después para conectar a BD real.
 */

@Named
@ViewScoped
public class GestionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private CourtService courtService;

    @Inject
    private ReservationService reservationService;

    @Inject
    private UserService userService;

    @Inject
    private AuthBean authBean;

    // === Tab activo ===
    private String activeTab = "dashboard";

    // === Búsqueda ===
    private String search = "";

    // === Formulario nueva organización ===
    private String newOrgName = "";
    private Zone newOrgZone = Zone.NORTE;
    private String newOrgAddress = "";
    private String newOrgPhone = "";
    private String newOrgDesc = "";
    private double newOrgLatitude = 0.0;
    private double newOrgLongitude = 0.0;

    // === Formulario nueva cancha ===
    private String newCourtOrg = "";
    private String newCourtName = "";
    private CourtType newCourtType = CourtType.FUTBOL;
    private double newCourtPrice = 0;
    private String newCourtSurface = "";
    private boolean newCourtLighting = true;
    private boolean newCourtCovered = false;

    // === Edición de cancha ===
    private Court editingCourt;
    private boolean showEditCourtModal = false;

    // === Edición de organización ===
    private Organization editingOrg;
    private boolean showEditOrgModal = false;

    // === Reportes ===
    private String reportDate = "2026-07-14";

    // === Listas para combos del formulario ===
    public Zone[] getZones() { return Zone.values(); }
    public CourtType[] getCourtTypes() { return CourtType.values(); }

    // === Dashboard stats ===
    public int getTotalCanchas() { return courtService.getCourtCount(); }
    public int getReservasActivas() { return reservationService.getActiveCount(); }
    public int getUsuarios() { return userService.getUserCount(); }
    public double getIngresosMes() { return reservationService.getMonthlyIncome(); }

    // === Organizaciones ===
    public List<Organization> getAllOrganizations() {
        return organizationService.getAll();
    }

    public String doAddOrganization() {
        // TODO: Implementar registro real con BD
        Organization org = new Organization();
        org.setName(newOrgName);
        org.setZone(newOrgZone);
        org.setAddress(newOrgAddress);
        org.setPhone(newOrgPhone);
        org.setDescription(newOrgDesc);
        org.setRating(4.5);
        org.setLatitude(newOrgLatitude);
        org.setLongitude(newOrgLongitude);
        organizationService.addOrganization(org);

        clearOrgForm();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Organización registrada exitosamente.", ""));
        return null;
    }

    public void editOrganization(Organization org) {
        this.editingOrg = org;
        this.showEditOrgModal = true;
    }

    public String doUpdateOrganization() {
        if (editingOrg != null) {
            organizationService.updateOrganization(editingOrg);
            showEditOrgModal = false;
            editingOrg = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Organización actualizada.", ""));
        }
        return null;
    }

    public void removeOrganization(String orgId) {
        organizationService.removeOrganization(orgId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Organización eliminada.", ""));
    }

    // === Canchas ===
    public List<Court> getAllCourts() {
        return courtService.getAll();
    }

    public String doAddCourt() {
        // TODO: Implementar registro real con BD
        if (newCourtOrg == null || newCourtOrg.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecciona una organización.", ""));
            return null;
        }
        Court court = new Court();
        court.setOrgId(newCourtOrg);
        court.setName(newCourtName);
        court.setType(newCourtType);
        court.setPricePerHour(newCourtPrice);
        court.setSurface(newCourtSurface);
        court.setHasLighting(newCourtLighting);
        court.setCovered(newCourtCovered);
        courtService.addCourt(court);

        clearCourtForm();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancha registrada exitosamente.", ""));
        return null;
    }

    public void editCourt(Court court) {
        this.editingCourt = court;
        this.showEditCourtModal = true;
    }

    public String doUpdateCourt() {
        if (editingCourt != null) {
            courtService.updateCourt(editingCourt);
            showEditCourtModal = false;
            editingCourt = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancha actualizada.", ""));
        }
        return null;
    }

    public void removeCourt(String courtId) {
        courtService.removeCourt(courtId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancha eliminada.", ""));
    }

    // === Reservas ===
    public List<Reservation> getAllReservations() {
        List<Reservation> all = reservationService.getAll();
        if (search == null || search.isEmpty()) return all;
        String lowerSearch = search.toLowerCase();
        return all.stream()
                .filter(r -> r.getContactName().toLowerCase().contains(lowerSearch)
                        || getCourtName(r.getCourtId()).toLowerCase().contains(lowerSearch)
                        || getOrgName(r.getOrgId()).toLowerCase().contains(lowerSearch))
                .toList();
    }

    public void cancelReservation(String reservationId) {
        reservationService.cancelReservation(reservationId);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva cancelada.", ""));
    }

    // === Helpers ===
    public String getCourtName(String courtId) {
        Court c = courtService.findById(courtId);
        return c != null ? c.getName() : "—";
    }

    public String getOrgName(String orgId) {
        Organization o = organizationService.findById(orgId);
        return o != null ? o.getName() : "—";
    }

    public String getOrgZoneName(String orgId) {
        Organization o = organizationService.findById(orgId);
        return o != null ? o.getZone().getLabel() : "—";
    }

    // === Limpiar formularios ===
    private void clearOrgForm() {
        newOrgName = "";
        newOrgZone = Zone.NORTE;
        newOrgAddress = "";
        newOrgPhone = "";
        newOrgDesc = "";
        newOrgLatitude = 0.0;
        newOrgLongitude = 0.0;
    }

    private void clearCourtForm() {
        newCourtOrg = "";
        newCourtName = "";
        newCourtType = CourtType.FUTBOL;
        newCourtPrice = 0;
        newCourtSurface = "";
        newCourtLighting = true;
        newCourtCovered = false;
    }

    // === Getters y Setters ===
    public String getActiveTab() { return activeTab; }
    public void setActiveTab(String activeTab) { this.activeTab = activeTab; }

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }

    // Org form
    public String getNewOrgName() { return newOrgName; }
    public void setNewOrgName(String v) { this.newOrgName = v; }
    public Zone getNewOrgZone() { return newOrgZone; }
    public void setNewOrgZone(Zone v) { this.newOrgZone = v; }
    public String getNewOrgAddress() { return newOrgAddress; }
    public void setNewOrgAddress(String v) { this.newOrgAddress = v; }
    public String getNewOrgPhone() { return newOrgPhone; }
    public void setNewOrgPhone(String v) { this.newOrgPhone = v; }
    public String getNewOrgDesc() { return newOrgDesc; }
    public void setNewOrgDesc(String v) { this.newOrgDesc = v; }
    public double getNewOrgLatitude() { return newOrgLatitude; }
    public void setNewOrgLatitude(double v) { this.newOrgLatitude = v; }
    public double getNewOrgLongitude() { return newOrgLongitude; }
    public void setNewOrgLongitude(double v) { this.newOrgLongitude = v; }

    // Court form
    public String getNewCourtOrg() { return newCourtOrg; }
    public void setNewCourtOrg(String v) { this.newCourtOrg = v; }
    public String getNewCourtName() { return newCourtName; }
    public void setNewCourtName(String v) { this.newCourtName = v; }
    public CourtType getNewCourtType() { return newCourtType; }
    public void setNewCourtType(CourtType v) { this.newCourtType = v; }
    public double getNewCourtPrice() { return newCourtPrice; }
    public void setNewCourtPrice(double v) { this.newCourtPrice = v; }
    public String getNewCourtSurface() { return newCourtSurface; }
    public void setNewCourtSurface(String v) { this.newCourtSurface = v; }
    public boolean isNewCourtLighting() { return newCourtLighting; }
    public void setNewCourtLighting(boolean v) { this.newCourtLighting = v; }
    public boolean isNewCourtCovered() { return newCourtCovered; }
    public void setNewCourtCovered(boolean v) { this.newCourtCovered = v; }

    // Edit modals
    public Court getEditingCourt() { return editingCourt; }
    public void setEditingCourt(Court v) { this.editingCourt = v; }
    public boolean isShowEditCourtModal() { return showEditCourtModal; }
    public void setShowEditCourtModal(boolean v) { this.showEditCourtModal = v; }
    public Organization getEditingOrg() { return editingOrg; }
    public void setEditingOrg(Organization v) { this.editingOrg = v; }
    public boolean isShowEditOrgModal() { return showEditOrgModal; }
    public void setShowEditOrgModal(boolean v) { this.showEditOrgModal = v; }

    public String getReportDate() { return reportDate; }
    public void setReportDate(String v) { this.reportDate = v; }
}

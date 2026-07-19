package unl.edu.ec.fieldPal.controller;

import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.model.enums.Zone;
import unl.edu.ec.fieldPal.service.CourtService;
import unl.edu.ec.fieldPal.service.OrganizationService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.ScheduleService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Managed Bean para la página de consulta de horarios.
 * Datos quemados - editar después para conectar a BD real.
 */
@Named
@ViewScoped
public class HorariosBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private CourtService courtService;

    @Inject
    private ScheduleService scheduleService;

    // Filtros
    private Zone selectedZone;
    private String selectedOrgId = "";
    private String selectedCourtId = "";
    private String date = LocalDate.now().toString();

    @PostConstruct
    public void init() {
        List<Organization> orgs = organizationService.getAll();
        if (!orgs.isEmpty()) {
            selectOrganization(orgs.get(0).getId());
        }
    }

    public List<Organization> getFilteredOrgs() {
        if (selectedZone != null) {
            return organizationService.getByZone(selectedZone);
        }
        return organizationService.getAll();
    }

    public List<Zone> getAvailableZones() {
        return organizationService.getAvailableZones();
    }

    public List<Court> getCourtsForSelectedOrg() {
        if (selectedOrgId == null || selectedOrgId.isEmpty()) return List.of();
        return courtService.getByOrg(selectedOrgId);
    }

    public Court getActiveCourt() {
        if (selectedCourtId == null || selectedCourtId.isEmpty()) return null;
        return courtService.findById(selectedCourtId);
    }

    public List<TimeSlot> getActiveSchedule() {
        if (selectedCourtId == null || selectedCourtId.isEmpty()) return List.of();
        return scheduleService.getSchedule(selectedCourtId, date);
    }

    public void filterByZone(Zone zone) {
        this.selectedZone = zone;
        List<Organization> filtered = getFilteredOrgs();
        selectedOrgId = "";
        selectedCourtId = "";
        if (!filtered.isEmpty()) {
            selectOrganization(filtered.get(0).getId());
        }
    }

    public void clearZoneFilter() {
        this.selectedZone = null;
        List<Organization> orgs = organizationService.getAll();
        selectedOrgId = "";
        selectedCourtId = "";
        if (!orgs.isEmpty()) {
            selectOrganization(orgs.get(0).getId());
        }
    }

    public void selectOrganization(String orgId) {
        this.selectedOrgId = orgId;
        List<Court> courts = courtService.getByOrg(orgId);
        this.selectedCourtId = courts.isEmpty() ? "" : courts.get(0).getId();
    }

    public void selectCourt(String courtId) {
        this.selectedCourtId = courtId;
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
}
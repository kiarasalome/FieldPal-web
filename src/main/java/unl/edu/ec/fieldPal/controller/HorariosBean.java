package unl.edu.ec.fieldPal.controller;

import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.model.enums.Zone;
import unl.edu.ec.fieldPal.service.security.CourtService;
import unl.edu.ec.fieldPal.service.security.OrganizationService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.security.ScheduleService;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author NeoCoreTeam
 * Managed Bean para la página de consulta de horarios.
 */
@Named
@ViewScoped
public class HorariosBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private CourtService courtService;

    @Inject
    private ScheduleService scheduleService;

    // Filtros
    private Zone selectedZone;
    private Long selectedOrgId = null;
    private Long selectedCourtId = null;
    private LocalDate date = LocalDate.now();

    @PostConstruct
    public void init() {
        List<Organization> orgs = organizationService.getAll();
        if (!orgs.isEmpty() && orgs.get(0).getId() != null) {
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
        if (selectedOrgId == null) return List.of();
        return courtService.getByOrg(selectedOrgId);
    }

    public Court getActiveCourt() {
        if (selectedCourtId == null) return null;
        return courtService.findById(selectedCourtId);
    }

    public List<TimeSlot> getActiveSchedule() {
        if (selectedCourtId == null) return List.of();
        return scheduleService.getSchedule(selectedCourtId, date);
    }

    public void filterByZone(Zone zone) {
        this.selectedZone = zone;
        List<Organization> filtered = getFilteredOrgs();
        selectedOrgId = null;
        selectedCourtId = null;
        if (!filtered.isEmpty()) {
            selectOrganization(filtered.get(0).getId());
        }
    }

    public void clearZoneFilter() {
        this.selectedZone = null;
        List<Organization> orgs = organizationService.getAll();
        selectedOrgId = null;
        selectedCourtId = null;
        if (!orgs.isEmpty()) {
            selectOrganization(orgs.get(0).getId());
        }
    }

    public void selectOrganization(Long orgId) {
        this.selectedOrgId = orgId;
        List<Court> courts = courtService.getByOrg(orgId);
        this.selectedCourtId = courts.isEmpty() ? null : courts.get(0).getId();
    }

    public void selectCourt(Long courtId) {
        this.selectedCourtId = courtId;
    }

    // Getters y Setters
    public Zone getSelectedZone() { return selectedZone; }
    public void setSelectedZone(Zone selectedZone) { this.selectedZone = selectedZone; }

    public Long getSelectedOrgId() { return selectedOrgId; }
    public void setSelectedOrgId(Long selectedOrgId) { this.selectedOrgId = selectedOrgId; }

    public Long getSelectedCourtId() { return selectedCourtId; }
    public void setSelectedCourtId(Long selectedCourtId) { this.selectedCourtId = selectedCourtId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
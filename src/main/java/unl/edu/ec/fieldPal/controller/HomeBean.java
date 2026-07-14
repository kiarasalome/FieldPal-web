package com.fieldPal.controller;

import com.fieldpal.model.Organization;
import com.fieldpal.model.enums.Zone;
import com.fieldpal.service.OrganizationService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Managed Bean para la página de inicio.
 * Datos quemados - editar después para conectar a BD real.
 */
@Named
@ViewScoped
public class HomeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    // Filtro de zona seleccionada
    private Zone selectedZone;

    // Datos de estadísticas
    private final int totalReservas = 3200;
    private final int totalCanchas = 24;
    private final int satisfaccion = 98;
    private final String atencion = "24/7";

    public List<Organization> getOrganizations() {
        if (selectedZone != null) {
            return organizationService.getByZone(selectedZone);
        }
        return organizationService.getAll();
    }

    public List<Zone> getAvailableZones() {
        return organizationService.getAvailableZones();
    }

    public void filterByZone(Zone zone) {
        this.selectedZone = zone;
    }

    public void clearZoneFilter() {
        this.selectedZone = null;
    }

    // Getters y Setters
    public Zone getSelectedZone() { return selectedZone; }
    public void setSelectedZone(Zone selectedZone) { this.selectedZone = selectedZone; }

    public int getTotalReservas() { return totalReservas; }
    public int getTotalCanchas() { return totalCanchas; }
    public int getSatisfaccion() { return satisfaccion; }
    public String getAtencion() { return atencion; }
}

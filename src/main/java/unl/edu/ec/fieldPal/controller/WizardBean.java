package unl.edu.ec.fieldPal.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.enums.Zone;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import unl.edu.ec.fieldPal.service.OrganizationService;
import unl.edu.ec.fieldPal.service.CourtService;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller para gestionar el asistente (Wizard) de registro de nuevos complejos deportivos.
 */
@Named("wizardBean")
@ViewScoped
public class WizardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrganizationService organizationService;

    @Inject
    private CourtService courtService;

    // Modelos principales del flujo
    private Organization newOrganization;
    private List<Court> tempCourts;
    private Court currentCourt; // Para el modal de añadir cancha

    // Variables del Paso 4 (Políticas y Horarios)
    private List<ScheduleDay> scheduleDays;
    private Integer reservationDepositPercentage;
    private boolean allowFreeCancellation;

    @PostConstruct
    public void init() {
        // Inicializar organización vacía
        newOrganization = new Organization();

        // Inicializar lista temporal de canchas
        tempCourts = new ArrayList<>();
        prepareNewCourt(); // Inicializa la cancha por defecto

        // Inicializar políticas por defecto
        reservationDepositPercentage = 50; // Inicia en 50% para el slider
        allowFreeCancellation = true;

        // Inicializar el configurador de horarios de 7 días con LocalTime
        scheduleDays = new ArrayList<>();
        scheduleDays.add(new ScheduleDay("Lunes", LocalTime.of(8, 0), LocalTime.of(22, 0), true));
        scheduleDays.add(new ScheduleDay("Martes", LocalTime.of(8, 0), LocalTime.of(22, 0), true));
        scheduleDays.add(new ScheduleDay("Miércoles", LocalTime.of(8, 0), LocalTime.of(22, 0), true));
        scheduleDays.add(new ScheduleDay("Jueves", LocalTime.of(8, 0), LocalTime.of(22, 0), true));
        scheduleDays.add(new ScheduleDay("Viernes", LocalTime.of(8, 0), LocalTime.of(23, 0), true));
        scheduleDays.add(new ScheduleDay("Sábado", LocalTime.of(8, 0), LocalTime.of(23, 0), true));
        scheduleDays.add(new ScheduleDay("Domingo", LocalTime.of(9, 0), LocalTime.of(21, 0), false)); // Desactivado por defecto
    }

    // === Lógica del Paso 2 (Canchas) ===

    /**
     * Prepara una instancia limpia de Court para ser llenada en el diálogo flotante.
     */
    public void prepareNewCourt() {
        currentCourt = new Court();
        currentCourt.setType(CourtType.FUTBOL); // Usando enum corregido en español
        currentCourt.setPricePerHour(15.0);    // Precio por defecto
        currentCourt.setSurface("Césped Sintético");
        currentCourt.setHasLighting(true);
        currentCourt.setCovered(false);
    }

    /**
     * Agrega la cancha que se configuró en el diálogo a la lista temporal del Wizard.
     */
    public void saveTempCourt() {
        if (currentCourt != null && currentCourt.getName() != null && !currentCourt.getName().trim().isEmpty()) {
            tempCourts.add(currentCourt);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Cancha '" + currentCourt.getName() + "' añadida temporalmente.", null));

            prepareNewCourt(); // Resetea el objeto para una futura inserción
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "El nombre de la cancha es obligatorio."));
        }
    }

    /**
     * Remueve una cancha de la lista temporal.
     */
    public void removeTempCourt(Court court) {
        if (tempCourts.remove(court)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Cancha '" + court.getName() + "' eliminada de la lista.", null));
        }
    }

    // === Lógica de Guardado Final (Paso 4) ===

    /**
     * Guarda la Organización, asocia las canchas temporales con el ID de la organización y persiste todo.
     */
    public String saveAll() {
        try {
            // 1. Guardar Organización
            organizationService.save(newOrganization);

            // 2. Asociar y guardar cada cancha temporal
            for (Court court : tempCourts) {
                court.setOrganizationId(newOrganization.getId());
                courtService.save(court);
            }

            // 3. Mostrar mensaje de éxito
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "¡Enhorabuena!", "El complejo '" + newOrganization.getName() + "' ha sido registrado exitosamente."));

            // Forzar que persista el mensaje a través de la redirección
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            // Redirección al Home limpio
            return "/home.xhtml?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al guardar", "Hubo un problema al registrar los datos: " + e.getMessage()));
            return null;
        }
    }

    // === Listas útiles para la Vista (Comboboxes) ===

    public Zone[] getZones() {
        return Zone.values();
    }

    public CourtType[] getCourtTypes() {
        return CourtType.values();
    }

    // === Getters y Setters ===

    public Organization getNewOrganization() {
        return newOrganization;
    }

    public void setNewOrganization(Organization newOrganization) {
        this.newOrganization = newOrganization;
    }

    public List<Court> getTempCourts() {
        return tempCourts;
    }

    public void setTempCourts(List<Court> tempCourts) {
        this.tempCourts = tempCourts;
    }

    public Court getCurrentCourt() {
        return currentCourt;
    }

    public void setCurrentCourt(Court currentCourt) {
        this.currentCourt = currentCourt;
    }

    public List<ScheduleDay> getScheduleDays() {
        return scheduleDays;
    }

    public void setScheduleDays(List<ScheduleDay> scheduleDays) {
        this.scheduleDays = scheduleDays;
    }

    public Integer getReservationDepositPercentage() {
        return reservationDepositPercentage;
    }

    public void setReservationDepositPercentage(Integer reservationDepositPercentage) {
        this.reservationDepositPercentage = reservationDepositPercentage;
    }

    public boolean isAllowFreeCancellation() {
        return allowFreeCancellation;
    }

    public void setAllowFreeCancellation(boolean allowFreeCancellation) {
        this.allowFreeCancellation = allowFreeCancellation;
    }

    // =========================================================================
    // CLASE AUXILIAR INTERNA: Representa el horario de un día individual de la semana
    // =========================================================================
    public static class ScheduleDay implements Serializable {

        private static final long serialVersionUID = 1L;

        private String dayName;
        private LocalTime openTime;
        private LocalTime closeTime;
        private boolean active;

        public ScheduleDay() {}

        public ScheduleDay(String dayName, LocalTime openTime, LocalTime closeTime, boolean active) {
            this.dayName = dayName;
            this.openTime = openTime;
            this.closeTime = closeTime;
            this.active = active;
        }

        public String getDayName() { return dayName; }
        public void setDayName(String dayName) { this.dayName = dayName; }

        public LocalTime getOpenTime() { return openTime; }
        public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }

        public LocalTime getCloseTime() { return closeTime; }
        public void setCloseTime(LocalTime closeTime) { this.closeTime = closeTime; }

        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
    }
}
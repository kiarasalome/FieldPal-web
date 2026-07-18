package unl.edu.ec.fieldPal.service;

import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.TimeSlot;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class CourtService {

    private final List<Court> courts = new ArrayList<>();
    private final Random random = new Random();

    public CourtService() {
        // Datos quemados con los nombres estándar de CourtType (SOCCER, VOLLEYBALL, TENNIS, PADEL)
        // Org 1 - Complejo Deportivo El Norte
        courts.add(new Court("c1", "o1", "Cancha Pro A", CourtType.FUTBOL, 25, true, false, "Césped Sintético", ""));
        courts.add(new Court("c2", "o1", "Cancha Pro B", CourtType.FUTBOL, 20, true, false, "Césped Sintético", ""));
        courts.add(new Court("c3", "o1", "Cancha Vóley", CourtType.VOLEY, 15, true, true, "Arena", ""));
        courts.add(new Court("c4", "o1", "Cancha Tenis 1", CourtType.TENNIS, 18, false, false, "Arcilla", ""));
        // Org 2 - Arena Sur Loja
        courts.add(new Court("c5", "o2", "Cancha Central", CourtType.FUTBOL, 22, true, true, "Césped Sintético", ""));
        courts.add(new Court("c6", "o2", "Cancha Vóley Arena", CourtType.VOLEY, 14, true, false, "Arena", ""));
        courts.add(new Court("c7", "o2", "Cancha Pádel 1", CourtType.PADEL, 20, true, true, "Goma", ""));
        // Org 3 - Club Deportivo Este
        courts.add(new Court("c8", "o3", "Tenis Norte", CourtType.TENNIS, 20, true, false, "Arcilla", ""));
        courts.add(new Court("c9", "o3", "Tenis Sur", CourtType.TENNIS, 20, true, false, "Dura", ""));
        courts.add(new Court("c10", "o3", "Pádel Premium", CourtType.PADEL, 25, true, true, "Goma", ""));
        // Org 4 - Polideportivo La Concordia
        courts.add(new Court("c11", "o4", "Fútbol 5 A", CourtType.FUTBOL, 18, true, true, "Césped Sintético", ""));
        courts.add(new Court("c12", "o4", "Fútbol 5 B", CourtType.FUTBOL, 18, true, false, "Césped Sintético", ""));
        courts.add(new Court("c13", "o4", "Cancha Vóley Techada", CourtType.VOLEY, 16, true, true, "Madera", ""));
        courts.add(new Court("c14", "o4", "Cancha Pádel", CourtType.PADEL, 22, true, false, "Goma", ""));
        courts.add(new Court("c15", "o4", "Cancha Tenis", CourtType.TENNIS, 19, false, false, "Arcilla", ""));
        // Org 5 - Centro Deportivo San Sebastián
        courts.add(new Court("c16", "o5", "Fútbol Centro A", CourtType.FUTBOL, 28, true, false, "Césped Sintético", ""));
        courts.add(new Court("c17", "o5", "Fútbol Centro B", CourtType.FUTBOL, 28, true, false, "Césped Sintético", ""));
        courts.add(new Court("c18", "o5", "Pádel Centro", CourtType.PADEL, 26, true, true, "Goma", ""));
        // Org 6 - Mega Canchas Loja
        courts.add(new Court("c19", "o6", "Fútbol Mega 1", CourtType.FUTBOL, 15, true, false, "Césped Sintético", ""));
        courts.add(new Court("c20", "o6", "Fútbol Mega 2", CourtType.FUTBOL, 15, true, false, "Césped Sintético", ""));
        courts.add(new Court("c21", "o6", "Vóley Mega", CourtType.VOLEY, 12, false, false, "Arena", ""));
        courts.add(new Court("c22", "o6", "Tenis Mega 1", CourtType.TENNIS, 16, false, false, "Dura", ""));
        courts.add(new Court("c23", "o6", "Tenis Mega 2", CourtType.TENNIS, 16, false, false, "Arcilla", ""));
        courts.add(new Court("c24", "o6", "Pádel Mega", CourtType.PADEL, 18, true, true, "Goma", ""));
    }

    public List<Court> getAll() {
        return new ArrayList<>(courts);
    }

    public List<Court> getByOrg(String orgId) {
        if (orgId == null) return new ArrayList<>();
        return courts.stream()
                .filter(c -> c.getOrgId().equals(orgId))
                .collect(Collectors.toList());
    }

    public List<Court> getByType(CourtType type) {
        return courts.stream()
                .filter(c -> c.getType() == type)
                .collect(Collectors.toList());
    }

    public Court findById(String id) {
        if (id == null) return null;
        return courts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Guarda una cancha en la lista. Si ya cuenta con ID único registrado, actualiza su información;
     * de lo contrario, la añade y autogenera su ID de forma segura.
     * (Este método es el invocado por WizardBean.java)
     */
    public void save(Court court) {
        if (court == null) return;

        if (court.getId() != null && findById(court.getId()) != null) {
            updateCourt(court);
        } else {
            addCourt(court);
        }
    }

    public void addCourt(Court court) {
        if (court == null) return;

        // Autogeneración del identificador secuencial si viene vacío
        if (court.getId() == null || court.getId().trim().isEmpty()) {
            String id = "c" + (courts.size() + 1);
            court.setId(id);
        }
        courts.add(court);
    }

    public void updateCourt(Court court) {
        if (court == null || court.getId() == null) return;

        for (int i = 0; i < courts.size(); i++) {
            if (courts.get(i).getId().equals(court.getId())) {
                courts.set(i, court);
                return;
            }
        }
    }

    public void removeCourt(String id) {
        if (id == null) return;
        courts.removeIf(c -> c.getId().equals(id));
    }

    public int getCourtCount() {
        return courts.size();
    }
}
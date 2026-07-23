package unl.edu.ec.fieldPal.service.security;

import unl.edu.ec.fieldPal.model.Court;
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
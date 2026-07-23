package unl.edu.ec.fieldPal.service.security;

import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.enums.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class OrganizationService {

    private final List<Organization> organizations = new ArrayList<>();

    public OrganizationService() {

    }

    public List<Organization> getAll() {
        return new ArrayList<>(organizations);
    }

    public List<Organization> getByZone(Zone zone) {
        return organizations.stream()
                .filter(o -> o.getZone() == zone)
                .collect(Collectors.toList());
    }

    public Organization findById(String id) {
        if (id == null) return null;
        return organizations.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Guarda una organización. Si ya existe bajo ese ID, actualiza sus campos;
     * si no existe, la añade como una nueva.
     * (Este es el método puente que invoca tu WizardBean.java)
     */
    public void save(Organization org) {
        if (org == null) return;

        // Buscamos si ya existe el ID de la organización en memoria
        Organization existing = findById(org.getId());
        if (existing != null) {
            updateOrganization(org);
        } else {
            addOrganization(org);
        }
    }

    public void addOrganization(Organization org) {
        if (org == null) return;

        // Si el ID viene vacío, nulo o sin configurar, autogeneramos uno seguro
        if (org.getId() == null || org.getId().trim().isEmpty()) {
            String id = "o" + (organizations.size() + 1);
            org.setId(id);
        }

        organizations.add(org);
    }

    public void updateOrganization(Organization org) {
        if (org == null || org.getId() == null) return;

        for (int i = 0; i < organizations.size(); i++) {
            if (organizations.get(i).getId().equals(org.getId())) {
                organizations.set(i, org);
                return;
            }
        }
    }

    public void removeOrganization(String id) {
        if (id == null) return;
        organizations.removeIf(o -> o.getId().equals(id));
    }

    public List<Zone> getAvailableZones() {
        return organizations.stream()
                .map(Organization::getZone)
                .distinct()
                .collect(Collectors.toList());
    }
}


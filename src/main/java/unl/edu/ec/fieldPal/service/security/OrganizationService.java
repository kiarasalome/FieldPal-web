package unl.edu.ec.fieldPal.service.security;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.enums.Zone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.*;

@Named
@ApplicationScoped
public class OrganizationService {

    @Inject
    private CrudGenericService crudService;

    public OrganizationService() {
    }

    public List<Organization> getAll() {
        return crudService.findWithQuery("SELECT o FROM Organization o", Collections.emptyMap());
    }

    public List<Organization> getByZone(Zone zone) {
        if (zone == null) return new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("zone", zone);
        return crudService.findWithNamedQuery("Organization.getByZone", params);
    }

    public Organization findById(Long id) {
        if (id == null) return null;
        Organization organization = crudService.find(Organization.class, id);
        if (organization == null) {
            throw new EntityNotFoundException("Organization no encontrada con [" + id + "]");
        }
        return organization;
    }

    /**
     * Guarda una organización. Si el ID es null, se asume que es una entidad nueva y se crea;
     * de lo contrario, se actualiza.
     * (Este es el método puente que invoca el WizardBean.java)
     */
    public <T extends Organization> T save(T organization) {
        if (organization == null) return null;

        if (organization.getId() == null) {
            return crudService.create(organization);
        } else {
            return crudService.update(organization);
        }
    }

    public void addOrganization(Organization org) {
        if (org == null) return;
        crudService.create(org);
    }

    public void updateOrganization(Organization org) {
        if (org == null || org.getId() == null) return;
        crudService.update(org);
    }

    public void removeOrganization(Organization org) {
        if (org == null || org.getId() == null) return;
        crudService.delete(Organization.class, org.getId());
    }

    public List<Zone> getAvailableZones() {
        return crudService.findWithQuery("SELECT DISTINCT o.zone FROM Organization o", Collections.emptyMap());
    }
}
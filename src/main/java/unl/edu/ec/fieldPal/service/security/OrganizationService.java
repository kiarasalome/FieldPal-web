package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.enums.Zone;
import unl.edu.ec.fieldPal.service.repository.OrganizationRepository;

import java.util.List;

@Named
@ApplicationScoped
public class OrganizationService {

    @Inject
    private OrganizationRepository organizationRepository;

    public OrganizationService() {
    }

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public List<Organization> getByZone(Zone zone) {
        if (zone == null) return getAll();
        return organizationRepository.findByZone(zone);
    }

    public Organization findById(Long id) throws EntityNotFoundException {
        Organization organization = organizationRepository.findById(id);
        if (organization == null){
            throw new EntityNotFoundException("Organization no encontrada con [" + id + "]");
        }
        return organization;
    }

    public Organization save(Organization organization) {
        if (organization == null) return null;
        return organizationRepository.save(organization);
    }

    public void addOrganization(Organization org) {
        if (org == null) return;
        organizationRepository.save(org);
    }

    public void updateOrganization(Organization org) {
        if (org == null || org.getId() == null) return;
        organizationRepository.save(org);
    }

    public void removeOrganization(Organization org) {
        if (org == null || org.getId() == null) return;
        organizationRepository.deleteById(org.getId());
    }

    public List<Zone> getAvailableZones() {
        return organizationRepository.findDistinctZones();
    }
}


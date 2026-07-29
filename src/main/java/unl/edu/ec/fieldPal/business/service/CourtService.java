package unl.edu.ec.fieldPal.business.service;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.domain.Court;
import unl.edu.ec.fieldPal.domain.Organization;
import unl.edu.ec.fieldPal.domain.enums.CourtType;
import unl.edu.ec.fieldPal.business.repository.CourtRepository;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class CourtService {

    @Inject
    private CourtRepository courtRepository;

    @Inject
    private OrganizationService organizationService;

    public CourtService() {
    }

    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    public List<Court> getByOrg(Long orgId) {
        if (orgId == null) return new ArrayList<>();
        return courtRepository.findByOrg(orgId);
    }

    public List<Court> getByType(CourtType type) {
        if (type == null) return new ArrayList<>();
        return courtRepository.findByType(type);
    }

    public Court findById(Long id) {
        if (id == null) return null;
        Court court = courtRepository.findById(id);
        if (court == null) {
            throw new EntityNotFoundException("Cancha no encontrada con [" + id + "]");
        }
        return court;
    }

    public void save(Court court) {
        if (court == null) return;
        courtRepository.save(court);
        syncCourtCount(court.getOrgId());
    }

    public void addCourt(Court court) {
        save(court);
    }

    public void updateCourt(Court court) {
        if (court == null || court.getId() == null) return;
        courtRepository.save(court);
    }

    public void removeCourt(Long id) {
        if (id == null) return;
        Court court = courtRepository.findById(id);
        Long orgId = court != null ? court.getOrgId() : null;
        courtRepository.deleteById(id);
        syncCourtCount(orgId);
    }

    public int getCourtCount() {
        return (int) courtRepository.count();
    }

    // El campo Organization.courtCount es un contador "de caché" para mostrar
    // rápido "X canchas" en homepage/search/horarios. Nadie lo recalculaba al
    // crear/eliminar canchas, así que se quedaba pegado en 0 para siempre.
    private void syncCourtCount(Long orgId) {
        if (orgId == null) return;
        try {
            Organization org = organizationService.findById(orgId);
            org.setCourtCount(courtRepository.findByOrg(orgId).size());
            organizationService.updateOrganization(org);
        } catch (EntityNotFoundException e) {
            // Organización inexistente (dato inconsistente); no hay nada que sincronizar.
        }
    }
}
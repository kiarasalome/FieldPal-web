package unl.edu.ec.fieldPal.service.security;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.*;

@Named
@ApplicationScoped
public class CourtService {

    @Inject
    private CrudGenericService crudService;

    public CourtService() {
    }

    public List<Court> getAll() {
        return crudService.findWithQuery("SELECT c FROM Court c", Collections.emptyMap());
    }

    public List<Court> getByOrg(Long orgId) {
        if (orgId == null) return new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("orgId", orgId);
        return crudService.findWithNamedQuery("Court.getByOrg", params);
    }

    public List<Court> getByType(CourtType type) {
        if (type == null) return new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        return crudService.findWithNamedQuery("Court.getByType", params);
    }

    public Court findById(Long id) {
        if (id == null) return null;
        Court court = crudService.find(Court.class, id);
        if (court == null) {
            throw new EntityNotFoundException("Cancha no encontrada con [" + id + "]");
        }
        return court;
    }

    /**
     * Guarda una cancha. Si ya existe una entidad persistida con ese ID, actualiza;
     * de lo contrario, la crea.
     * (Este método es el invocado por WizardBean.java)
     */
    public void save(Court court) {
        if (court == null) return;

        if (court.getId() != null && crudService.find(Court.class, court.getId()) != null) {
            crudService.update(court);
        } else {
            crudService.create(court);
        }
    }

    public void addCourt(Court court) {
        if (court == null) return;
        crudService.create(court);
    }

    public void updateCourt(Court court) {
        if (court == null || court.getId() == null) return;
        crudService.update(court);
    }

    public void removeCourt(Long id) {
        if (id == null) return;
        crudService.delete(Court.class, id);
    }

    public long getCourtCount() {
        List<Long> result = crudService.findWithQuery(
                "SELECT COUNT(c) FROM Court c", Collections.emptyMap());
        return result.isEmpty() ? 0L : result.get(0);
    }
}
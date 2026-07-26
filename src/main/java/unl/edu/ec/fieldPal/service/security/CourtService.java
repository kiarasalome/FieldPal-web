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
        String jpql = "SELECT DISTINCT o.courts FROM Organization o";
        return crudService.findWithQuery(jpql, Collections.emptyMap());
    }

    public List<Court> getByOrg(Long orgId){
        if (orgId == null) return new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("orgId", orgId);
        return crudService.findWithNamedQuery("Court.getByOrg", params);
    }

    public List<Court> getByType(CourtType type){
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        return crudService.findWithNamedQuery("Court.getByType", params);
    }

    public Court findById(Long id) {
        if (id == null) return null;
        Court court = crudService.find(Court.class, id);
        if (court == null){
            throw new EntityNotFoundException("Cancha no encontrada con [" + id + "]");
        }
        return court;
    }

    /**
     * Guarda una cancha en la lista. Si ya cuenta con ID único registrado, actualiza su información;
     * de lo contrario, la añade y autogenera su ID de forma segura.
     * (Este método es el invocado por WizardBean.java)
     */
    public void save(Court court) {
        if (court == null) return;

        if (court.getId() != null && findById(court.getId()) != null) {
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

    public int getCourtCount() {
        return getAll().size();
    }
}
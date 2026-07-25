package unl.edu.ec.fieldPal.service.repository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class CourtRepository {

    @Inject
    private CrudGenericService crud;

    public Court save(Court court) {
        if (crud.find(Court.class, court.getId()) == null) {
            return crud.create(court);
        }
        return crud.update(court);
    }

    public Court findById(String id) {
        if (id == null) return null;
        return crud.find(Court.class, id);
    }

    public List<Court> findAll() {
        return crud.findWithQuery("SELECT c FROM Court c", Court.class);
    }

    public List<Court> findByOrg(String orgId) {
        if (orgId == null) return List.of();
        Map<String, Object> params = new HashMap<>();
        params.put("orgId", orgId);
        return crud.findWithQuery("SELECT c FROM Court c WHERE c.orgId = :orgId", Court.class, params);
    }

    public List<Court> findByType(CourtType type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        return crud.findWithQuery("SELECT c FROM Court c WHERE c.type = :type", Court.class, params);
    }

    public void deleteById(String id) {
        crud.delete(Court.class, id);
    }

    public long count() {
        return crud.count("SELECT COUNT(c) FROM Court c");
    }
}

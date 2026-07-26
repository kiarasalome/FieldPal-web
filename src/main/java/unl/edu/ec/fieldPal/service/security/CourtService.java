package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import unl.edu.ec.fieldPal.service.repository.CourtRepository;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class CourtService {

    @Inject
    private CourtRepository courtRepository;

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
    }

    public void addCourt(Court court) {
        if (court == null) return;
        courtRepository.save(court);
    }

    public void updateCourt(Court court) {
        if (court == null || court.getId() == null) return;
        courtRepository.save(court);
    }

    public void removeCourt(Long id) {
        if (id == null) return;
        courtRepository.deleteById(id);
    }

    public int getCourtCount() {
        return (int) courtRepository.count();
    }
}
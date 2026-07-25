package unl.edu.ec.fieldPal.service;

import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.enums.CourtType;
import unl.edu.ec.fieldPal.service.repository.CourtRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class CourtService {

    @Inject
    private CourtRepository courtRepository;

    @PostConstruct
    @Transactional
    public void seedIfEmpty() {
        if (courtRepository.count() > 0) return;

        // Org 1 - Complejo Deportivo El Norte
        courtRepository.save(new Court("c1", "o1", "Cancha Pro A", CourtType.FUTBOL, 25, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c2", "o1", "Cancha Pro B", CourtType.FUTBOL, 20, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c3", "o1", "Cancha Vóley", CourtType.VOLEY, 15, true, true, "Arena", ""));
        courtRepository.save(new Court("c4", "o1", "Cancha Tenis 1", CourtType.TENNIS, 18, false, false, "Arcilla", ""));
        // Org 2 - Arena Sur Loja
        courtRepository.save(new Court("c5", "o2", "Cancha Central", CourtType.FUTBOL, 22, true, true, "Césped Sintético", ""));
        courtRepository.save(new Court("c6", "o2", "Cancha Vóley Arena", CourtType.VOLEY, 14, true, false, "Arena", ""));
        courtRepository.save(new Court("c7", "o2", "Cancha Pádel 1", CourtType.PADEL, 20, true, true, "Goma", ""));
        // Org 3 - Club Deportivo Este
        courtRepository.save(new Court("c8", "o3", "Tenis Norte", CourtType.TENNIS, 20, true, false, "Arcilla", ""));
        courtRepository.save(new Court("c9", "o3", "Tenis Sur", CourtType.TENNIS, 20, true, false, "Dura", ""));
        courtRepository.save(new Court("c10", "o3", "Pádel Premium", CourtType.PADEL, 25, true, true, "Goma", ""));
        // Org 4 - Polideportivo La Concordia
        courtRepository.save(new Court("c11", "o4", "Fútbol 5 A", CourtType.FUTBOL, 18, true, true, "Césped Sintético", ""));
        courtRepository.save(new Court("c12", "o4", "Fútbol 5 B", CourtType.FUTBOL, 18, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c13", "o4", "Cancha Vóley Techada", CourtType.VOLEY, 16, true, true, "Madera", ""));
        courtRepository.save(new Court("c14", "o4", "Cancha Pádel", CourtType.PADEL, 22, true, false, "Goma", ""));
        courtRepository.save(new Court("c15", "o4", "Cancha Tenis", CourtType.TENNIS, 19, false, false, "Arcilla", ""));
        // Org 5 - Centro Deportivo San Sebastián
        courtRepository.save(new Court("c16", "o5", "Fútbol Centro A", CourtType.FUTBOL, 28, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c17", "o5", "Fútbol Centro B", CourtType.FUTBOL, 28, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c18", "o5", "Pádel Centro", CourtType.PADEL, 26, true, true, "Goma", ""));
        // Org 6 - Mega Canchas Loja
        courtRepository.save(new Court("c19", "o6", "Fútbol Mega 1", CourtType.FUTBOL, 15, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c20", "o6", "Fútbol Mega 2", CourtType.FUTBOL, 15, true, false, "Césped Sintético", ""));
        courtRepository.save(new Court("c21", "o6", "Vóley Mega", CourtType.VOLEY, 12, false, false, "Arena", ""));
        courtRepository.save(new Court("c22", "o6", "Tenis Mega 1", CourtType.TENNIS, 16, false, false, "Dura", ""));
        courtRepository.save(new Court("c23", "o6", "Tenis Mega 2", CourtType.TENNIS, 16, false, false, "Arcilla", ""));
        courtRepository.save(new Court("c24", "o6", "Pádel Mega", CourtType.PADEL, 18, true, true, "Goma", ""));
    }

    public List<Court> getAll() {
        return courtRepository.findAll();
    }

    public List<Court> getByOrg(String orgId) {
        return courtRepository.findByOrg(orgId);
    }

    public List<Court> getByType(CourtType type) {
        return courtRepository.findByType(type);
    }

    public Court findById(String id) {
        return courtRepository.findById(id);
    }

    /**
     * Guarda una cancha en la BD. Si ya cuenta con ID único registrado, actualiza su información;
     * de lo contrario, la añade y autogenera su ID de forma segura.
     * (Este método es el invocado por WizardBean.java)
     */
    @Transactional
    public void save(Court court) {
        if (court == null) return;
        if (court.getId() == null || court.getId().trim().isEmpty()) {
            court.setId("c" + (courtRepository.count() + 1));
        }
        courtRepository.save(court);
    }

    @Transactional
    public void addCourt(Court court) {
        save(court);
    }

    @Transactional
    public void updateCourt(Court court) {
        if (court == null || court.getId() == null) return;
        courtRepository.save(court);
    }

    @Transactional
    public void removeCourt(String id) {
        if (id == null) return;
        courtRepository.deleteById(id);
    }

    public int getCourtCount() {
        return (int) courtRepository.count();
    }
}

package unl.edu.ec.fieldPal.service;

import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.enums.Zone;
import unl.edu.ec.fieldPal.service.repository.OrganizationRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class OrganizationService {

    @Inject
    private OrganizationRepository organizationRepository;

    @PostConstruct
    @Transactional
    public void seedIfEmpty() {
        if (organizationRepository.count() > 0) return;

        organizationRepository.save(new Organization("o1", "Complejo Deportivo El Norte", Zone.NORTE,
                "Av. Cevallos y 10 de Agosto", "+593 99 111 2233", "", 4.8,
                "Instalaciones de primer nivel con césped sintético FIFA.", 4,
                -3.9931, -79.2042));
        organizationRepository.save(new Organization("o2", "Arena Sur Loja", Zone.SUR,
                "Av. Universitaria y Rocafuerte", "+593 99 222 3344", "", 4.5,
                "Complejo deportivo con canchas techadas y al aire libre.", 3,
                -4.0080, -79.2100));
        organizationRepository.save(new Organization("o3", "Club Deportivo Este", Zone.ESTE,
                "Calle Santiago y Sucre", "+593 99 333 4455", "", 4.6,
                "Canchas de tenis y pádel con iluminación LED.", 3,
                -3.9950, -79.1900));
        organizationRepository.save(new Organization("o4", "Polideportivo La Concordia", Zone.OESTE,
                "Av. 17 de Septiembre", "+593 99 444 5566", "", 4.3,
                "El polideportivo más completo del oeste de Loja.", 5,
                -4.0010, -79.2200));
        organizationRepository.save(new Organization("o5", "Centro Deportivo San Sebastián", Zone.CENTRO,
                "Calle Bolívar y Juan de Salinas", "+593 99 555 6677", "", 4.7,
                "En el corazón de Loja, acceso rápido y fácil.", 3,
                -3.9970, -79.2050));
        organizationRepository.save(new Organization("o6", "Mega Canchas Loja", Zone.NORTE,
                "Vía a Catamayo Km 3", "+593 99 666 7788", "", 4.4,
                "Espacios amplios con parking gratuito.", 6,
                -3.9850, -79.1950));
    }

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public List<Organization> getByZone(Zone zone) {
        return organizationRepository.findByZone(zone);
    }

    public Organization findById(String id) {
        return organizationRepository.findById(id);
    }

    /**
     * Guarda una organización. Si ya existe bajo ese ID, actualiza sus campos;
     * si no existe, la añade como una nueva.
     * (Este es el método puente que invoca tu WizardBean.java)
     */
    @Transactional
    public void save(Organization org) {
        if (org == null) return;
        if (org.getId() == null || org.getId().trim().isEmpty()) {
            org.setId("o" + (organizationRepository.count() + 1));
        }
        organizationRepository.save(org);
    }

    @Transactional
    public void addOrganization(Organization org) {
        save(org);
    }

    @Transactional
    public void updateOrganization(Organization org) {
        if (org == null || org.getId() == null) return;
        organizationRepository.save(org);
    }

    @Transactional
    public void removeOrganization(String id) {
        if (id == null) return;
        organizationRepository.deleteById(id);
    }

    public List<Zone> getAvailableZones() {
        return organizationRepository.findDistinctZones();
    }
}

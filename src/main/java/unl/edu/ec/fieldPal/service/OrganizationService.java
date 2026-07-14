package com.fieldPal.service;


import com.fieldPal.model.Organization;
import com.fieldPal.model.enums.Zone;
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
        // Datos quemados - editar después para conectar a BD
        organizations.add(new Organization("o1", "Complejo Deportivo El Norte", Zone.NORTE,
                "Av. Cevallos y 10 de Agosto", "+593 99 111 2233", "", 4.8,
                "Instalaciones de primer nivel con césped sintético FIFA.", 4,
                -3.9931, -79.2042));
        organizations.add(new Organization("o2", "Arena Sur Loja", Zone.SUR,
                "Av. Universitaria y Rocafuerte", "+593 99 222 3344", "", 4.5,
                "Complejo deportivo con canchas techadas y al aire libre.", 3,
                -4.0080, -79.2100));
        organizations.add(new Organization("o3", "Club Deportivo Este", Zone.ESTE,
                "Calle Santiago y Sucre", "+593 99 333 4455", "", 4.6,
                "Canchas de tenis y pádel con iluminación LED.", 3,
                -3.9950, -79.1900));
        organizations.add(new Organization("o4", "Polideportivo La Concordia", Zone.OESTE,
                "Av. 17 de Septiembre", "+593 99 444 5566", "", 4.3,
                "El polideportivo más completo del oeste de Loja.", 5,
                -4.0010, -79.2200));
        organizations.add(new Organization("o5", "Centro Deportivo San Sebastián", Zone.CENTRO,
                "Calle Bolívar y Juan de Salinas", "+593 99 555 6677", "", 4.7,
                "En el corazón de Loja, acceso rápido y fácil.", 3,
                -3.9970, -79.2050));
        organizations.add(new Organization("o6", "Mega Canchas Loja", Zone.NORTE,
                "Vía a Catamayo Km 3", "+593 99 666 7788", "", 4.4,
                "Espacios amplios con parking gratuito.", 6,
                -3.9850, -79.1950));
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
        return organizations.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void addOrganization(Organization org) {
        String id = "o" + (organizations.size() + 1);
        org.setId(id);
        organizations.add(org);
    }

    public void updateOrganization(Organization org) {
        for (int i = 0; i < organizations.size(); i++) {
            if (organizations.get(i).getId().equals(org.getId())) {
                organizations.set(i, org);
                return;
            }
        }
    }

    public void removeOrganization(String id) {
        organizations.removeIf(o -> o.getId().equals(id));
    }

    public List<Zone> getAvailableZones() {
        return organizations.stream()
                .map(Organization::getZone)
                .distinct()
                .collect(Collectors.toList());
    }
}


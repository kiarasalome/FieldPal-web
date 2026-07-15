package unl.edu.ec.fieldPal.controller;

import unl.edu.ec.fieldPal.model.Organization;
import unl.edu.ec.fieldPal.model.Court;
import unl.edu.ec.fieldPal.model.enums.CourtType;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped; // ViewScoped correcto para JSF, sin dependencias nuevas
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

// Bean de la vista de búsqueda de organizaciones para el jugador.
// Trabaja con Organization (el local/negocio) y Court (cada cancha dentro de ese local).
@Named
@ViewScoped
public class OrganizationBean implements Serializable {

    private List<Organization> organizations;         // todas las organizaciones, sin filtrar
    private List<Organization> filteredOrganizations;  // lo que realmente se muestra en pantalla
    private List<Court> courts;                          // todas las canchas de todas las organizaciones
    private String searchTerm;                           // texto escrito en el buscador
    private String selectedSport = "";                   // qué chip de deporte está activo

    // Ids de organizaciones marcadas como favoritas por el jugador actual.
    // TODO: esto debería moverse a un bean de sesión/usuario o a la base de datos,
    // para que no se pierda al cambiar de página. Organization NO debe tener este campo,
    // porque esa entidad es compartida por todos los usuarios, no es individual por jugador.
    private Set<String> favoriteIds = new HashSet<>();

    @PostConstruct
    public void init() {
        organizations = loadOrganizations();
        courts = loadCourts();
        filteredOrganizations = new ArrayList<>(organizations);
        searchTerm = "";
    }

    // Filtra organizaciones por nombre, zona, o por el tipo de cancha que ofrecen
    public void search() {
        String term = (searchTerm == null) ? "" : searchTerm.toLowerCase().trim();

        filteredOrganizations = organizations.stream()
                .filter(o -> term.isEmpty()
                        || o.getName().toLowerCase().contains(term)
                        || o.getZone().getLabel().toLowerCase().contains(term)
                        || courtsOf(o).stream()
                        .anyMatch(c -> c.getType().getLabel().toLowerCase().contains(term)))
                .collect(Collectors.toList());
    }

    // Se llama al hacer clic en un chip de deporte (Fútbol, Vóley, Tenis, Pádel)
    public void filterBySport(String sportLabel) {
        this.selectedSport = sportLabel;
        this.searchTerm = sportLabel;
        search();
    }

    // Agrega o quita una organización de favoritos
    public void toggleFavorite(Organization org) {
        if (favoriteIds.contains(org.getId())) {
            favoriteIds.remove(org.getId());
        } else {
            favoriteIds.add(org.getId());
        }
    }

    // El xhtml usa esto para saber si pinta el corazón lleno o vacío
    public boolean isFavorite(Organization org) {
        return favoriteIds.contains(org.getId());
    }

    // Devuelve solo las canchas que pertenecen a una organización específica,
    // comparando el orgId de cada Court contra el id de la Organization
    public List<Court> courtsOf(Organization org) {
        return courts.stream()
                .filter(c -> c.getOrgId().equals(org.getId()))
                .collect(Collectors.toList());
    }

    // El precio "desde" que se muestra en la tarjeta = el más barato entre todas sus canchas
    public double priceFrom(Organization org) {
        return courtsOf(org).stream()
                .mapToDouble(Court::getPricePerHour)
                .min()
                .orElse(0);
    }

    // Genera las etiquetas de la tarjeta a partir de los tipos de cancha que tiene la organización,
    // usando el label en español (CourtType.getLabel()) para mostrarlo al usuario, sin repetir tipos
    public List<String> tagsOf(Organization org) {
        return courtsOf(org).stream()
                .map(c -> c.getType().getLabel())
                .distinct()
                .collect(Collectors.toList());
    }

    // Datos de ejemplo. Reemplaza esto por tu servicio/EJB real cuando tengas la conexión a base de datos.
    private List<Organization> loadOrganizations() {
        List<Organization> list = new ArrayList<>();
        list.add(new Organization("1", "Estadio Metropolitano", unl.edu.ec.fieldPal.model.enums.Zone.NORTE,
                "Distrito Norte, Loja", "0999999999", "https://via.placeholder.com/400x200",
                4.9, "Complejo de fútbol profesional", 2, -3.99, -79.20));
        list.add(new Organization("2", "Pádel Center Elite", unl.edu.ec.fieldPal.model.enums.Zone.SUR,
                "Zona Sur, Loja", "0988888888", "https://via.placeholder.com/400x200",
                4.7, "Pistas de pádel techadas", 1, -3.99, -79.20));
        return list;
    }

    private List<Court> loadCourts() {
        List<Court> list = new ArrayList<>();
        list.add(new Court("c1", "1", "Cancha Principal", CourtType.FUTBOL, 45,
                true, false, "Césped sintético", "https://via.placeholder.com/400x200"));
        list.add(new Court("c2", "1", "Cancha Secundaria", CourtType.FUTBOL, 30,
                true, false, "Césped sintético", "https://via.placeholder.com/400x200"));
        list.add(new Court("c3", "2", "Pista 1", CourtType.PADEL, 28,
                true, true, "Cristal", "https://via.placeholder.com/400x200"));
        return list;
    }

    // Getters/setters — necesarios para que el XHTML lea/escriba estos valores por EL
    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }

    public List<Organization> getFilteredOrganizations() { return filteredOrganizations; }

    public String getSelectedSport() { return selectedSport; }
}
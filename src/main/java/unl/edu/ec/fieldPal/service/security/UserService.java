package unl.edu.ec.fieldPal.service.security;

import jakarta.inject.Inject;
import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.service.CrudGenericService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NeoCoreTeam
 */

@Named
@ApplicationScoped
public class UserService {

    @Inject
    private CrudGenericService crudService; // Inyección obligatoria

    //private final List<User> users = new ArrayList<>();

    public UserService() {
//        Datos quemados - editar después para conectar a BD
//        users.add(new User("1", "Admin FieldPal", "admin@fieldpal.com",
//                "+593 99 000 0001", "admin123", UserRole.ADMIN));
//        users.add(new User("2", "Carlos Mendoza", "jugador@fieldpal.com",
//                "+593 99 123 4567", "jugador123", UserRole.PLAYER));
    }

    public User login(String email, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        // Uso de NamedQuery definida en la entidad User [4, 5]
        return crudService.findSingleResultOrNullWithNamedQuery("User.login", params);
    }

    public User register(String name, String email, String phone, String password, UserRole role) {
        //Se verificaría si el email ya existe en BD
        User newUser = new User(null, name, email, phone, password, role);
        return crudService.create(newUser);
    }

    public void updateUser(User user) {
        if (user == null || user.getId() == null) return;
        crudService.update(user); // Actualización en base de datos
    }

    public User findById(Long id) {
        return crudService.find(User.class, id);    }

    //ATENCION CON LOS SIGUIENTES METODOS

    /**
     *"SELECT COUNT(u): pide a la base de datos que cuente, en lugar de
     * traer todos los datos a la memoria solo para saber cuantos hay
     * Se evita codigo spaghetti
     */

    public int getUserCount() {
        // Consulta JPQL para contar usuarios directamente en la base de datos
        String jpql = "SELECT COUNT(u) FROM User u";
        List<Long> result = crudService.findWithQuery(jpql, new HashMap<>());
        return result.get(0).intValue();
    }

    public List<User> getAllUsers() {
        // Consulta JPQL para obtener todos los registros de la tabla users
        String jpql = "SELECT u FROM User u";
        return crudService.findWithQuery(jpql, new HashMap<>());
    }
}

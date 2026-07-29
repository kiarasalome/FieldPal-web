package unl.edu.ec.fieldPal.business.service;

import jakarta.inject.Inject;
import unl.edu.ec.fieldPal.domain.User;
import unl.edu.ec.fieldPal.domain.enums.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.business.genericService.CrudGenericService;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    private CrudGenericService crudService;

    public UserService() {
    }

    public User login(String email, String password) {
        if (email == null || password == null) return null;

        // TODO SEGURIDAD: aquí se compara la contraseña en texto plano contra la BD.
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return crudService.findSingleResultOrNullWithNamedQuery("User.login", params);
    }

    public User register(String name, String email, String phone, String password, UserRole role) {
        if (email == null) return null;

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        List<User> existing = crudService.findWithQuery(
                "SELECT u FROM User u WHERE u.email = :email", params);
        if (!existing.isEmpty()) {
            return null; // Ya existe una cuenta con este email
        }

        // TODO SEGURIDAD: password debería guardarse como hash, no en texto plano.
        User newUser = new User(null, name, email, phone, password, role);
        return crudService.create(newUser);
    }

    public void updateUser(User user) {
        if (user == null || user.getId() == null) return;
        crudService.update(user);
    }

    public int getUserCount() {
        String jpql = "SELECT COUNT(u) FROM User u";
        List<Long> result = crudService.findWithQuery(jpql, new HashMap<>());
        return result.isEmpty() ? 0 : result.get(0).intValue();
    }

    public List<User> getAllUsers() {
        String jpql = "SELECT u FROM User u";
        return crudService.findWithQuery(jpql, new HashMap<>());
    }
}
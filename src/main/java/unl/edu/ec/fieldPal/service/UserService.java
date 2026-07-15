package unl.edu.ec.fieldPal.service;

import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Datos quemados - editar después para conectar a BD
        users.add(new User("1", "Admin FieldPal", "admin@fieldpal.com",
                "+593 99 000 0001", "admin123", UserRole.ADMIN));
        users.add(new User("2", "Carlos Mendoza", "jugador@fieldpal.com",
                "+593 99 123 4567", "jugador123", UserRole.PLAYER));
    }

    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User register(String name, String email, String phone, String password, UserRole role) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return null; // Ya existe
            }
        }
        String id = String.valueOf(users.size() + 1);
        User newUser = new User(id, name, email, phone, password, role);
        users.add(newUser);
        return newUser;
    }

    //Metodo conectado al updateUser para modificar data de un player
    public void updateUser(User user) {
        if (user == null || user.getId() == null) return;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user); // Reemplaza el usuario viejo por el editado
                return;
            }
        }
    }

    public User findById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public int getUserCount() {
        return users.size();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}

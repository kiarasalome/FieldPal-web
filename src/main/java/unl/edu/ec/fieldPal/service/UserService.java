package unl.edu.ec.fieldPal.service;

import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import unl.edu.ec.fieldPal.service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    /**
     * Siembra los usuarios demo la primera vez que arranca la app contra una
     * base de datos vacía (equivalente a los "datos quemados" que antes vivían
     * en el constructor, ahora ya persistidos en Postgres).
     */
    @PostConstruct
    @Transactional
    public void seedIfEmpty() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("1", "Admin FieldPal", "admin@fieldpal.com",
                    "+593 99 000 0001", "admin123", UserRole.ADMIN));
            userRepository.save(new User("2", "Carlos Mendoza", "jugador@fieldpal.com",
                    "+593 99 123 4567", "jugador123", UserRole.PLAYER));
        }
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        return user.getPassword().equals(password) ? user : null;
    }

    @Transactional
    public User register(String name, String email, String phone, String password, UserRole role) {
        if (userRepository.findByEmail(email) != null) {
            return null; // Ya existe
        }
        User newUser = new User(UUID.randomUUID().toString(), name, email, phone, password, role);
        return userRepository.save(newUser);
    }

    // Método conectado al updateUser para modificar data de un player
    @Transactional
    public void updateUser(User user) {
        if (user == null || user.getId() == null) return;
        userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id);
    }

    public int getUserCount() {
        return (int) userRepository.count();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

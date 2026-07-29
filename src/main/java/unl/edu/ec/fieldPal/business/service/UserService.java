package unl.edu.ec.fieldPal.business.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.domain.User;
import unl.edu.ec.fieldPal.domain.enums.UserRole;
import unl.edu.ec.fieldPal.business.repository.UserRepository;

import java.util.List;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    public UserService() {
    }

    public User login(String email, String password) {
        if (email == null || password == null) return null;

        // TODO SEGURIDAD: aquí se compara la contraseña en texto plano contra la BD.
        return userRepository.login(email, password);
    }

    public User register(String name, String email, String phone, String password, UserRole role) {
        if (email == null) return null;

        if (userRepository.findByEmail(email) != null) {
            return null; // Ya existe una cuenta con este email
        }

        // TODO SEGURIDAD: password debería guardarse como hash, no en texto plano.
        User newUser = new User(null, name, email, phone, password, role);
        return userRepository.save(newUser);
    }

    public void updateUser(User user) {
        if (user == null || user.getId() == null) return;
        userRepository.save(user);
    }

    public int getUserCount() {
        return (int) userRepository.count();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
package unl.edu.ec.fieldPal.service.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import unl.edu.ec.fieldPal.service.repository.UserRepository;

import java.util.List;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    public UserService() {
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(String name, String email, String phone, String password, UserRole role) {
        if (userRepository.findByEmail(email) != null) {
            return null; // Ya existe
        }
        User newUser = new User(name, email, phone, password, role);
        return userRepository.save(newUser);
    }

    public void updateUser(User user) {
        if (user == null || user.getId() == null) return;
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public int getUserCount() {
        return (int) userRepository.count();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

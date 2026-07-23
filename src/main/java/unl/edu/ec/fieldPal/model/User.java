package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.fieldPal.model.enums.UserRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users") // Usamos "users" en plural porque "user" es palabra reservada en PostgreSQL
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "name", nullable = false, length = 150)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Ingrese una dirección de correo válida")
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "password", nullable = false, length = 255) // Tamaño adecuado para hashes BCrypt / PBKDF2
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    @NotNull(message = "Debe asignar un rol al usuario")
    private UserRole role;

    public User() {}

    public User(String id, String name, String email, String phone, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // === Métodos Helper de Roles ===

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isPlayer() {
        return role == UserRole.PLAYER;
    }

    // === Getters y Setters ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    // === Equals & HashCode ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

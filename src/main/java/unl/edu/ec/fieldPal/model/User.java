package unl.edu.ec.fieldPal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author NeoCoreTeam
 * Entidad que representa a un usuario
 * Se utiliza "users" en plural debido a que "user" es una palabra reservada en PostgreSQL
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.login",
                query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password"),

        @NamedQuery(name = "User.findAll",
                query = "SELECT u FROM User u"),

        @NamedQuery(name = "User.findByEmail",
                query = "SELECT u FROM User u WHERE u.email = :email")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Ingrese una dirección de correo válida")
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    @NotNull(message = "Debe asignar un rol al usuario")
    private UserRole role; // PLAYER o ADMIN. [2]

    public User() {}

    public User(Long id, String name, String email, String phone, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    // Métodos de Lógica de Negocio

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isPlayer() {
        return role == UserRole.PLAYER;
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
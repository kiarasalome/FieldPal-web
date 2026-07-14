package unl.edu.ec.fieldPal.domain.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Role implements Serializable {

    private Long id;

    @NotNull @NotEmpty
    private String name;

    private String description;

    private Set<Permission> permissions;

    public Role() {
        permissions = new HashSet<>();
    }

    public Role(Long id, @NotNull @NotEmpty String name, String description) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Role(@NotNull @NotEmpty String name, String description) {
        this(null, name, description);
    }

    public void add(Permission permission){
        if (permission != null) {
            if (!getPermissions().contains(permission)){
                this.permissions.add(permission);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull  @NotEmpty String name) {
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) && Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}

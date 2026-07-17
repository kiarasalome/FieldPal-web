package unl.edu.ec.fieldPal.domain.security;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Permission implements Serializable {

    private Long id;

    /**
     * Representa un recurso URI. Ej.: "/admin/usuarios"
     */
    @NotNull @NotEmpty
    private String resource;

    @NotNull
    private ActionType action;

    public Permission() {
        action = ActionType.ALL;
    }

    public Permission(Long id, @NotNull @NotEmpty String resource, @NotNull @NotEmpty ActionType action) {
        this.id = id;
        this.resource = resource;
        this.action = action;
    }

    public Permission(@NotNull @NotEmpty String resource, @NotNull ActionType action) {
        this(null, resource, action);
    }

    public boolean matchWith(String requestResource, ActionType requestActionType) {
        return this.resource.equals(requestResource) &&
                (this.action == ActionType.ALL || action.equals(requestActionType)) ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getResource(), that.getResource()) && getAction() == that.getAction();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getResource(), getAction());
    }
}

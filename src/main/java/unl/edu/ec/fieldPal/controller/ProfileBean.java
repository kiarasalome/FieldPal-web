package unl.edu.ec.fieldPal.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;


import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.service.UserService;
import unl.edu.ec.fieldPal.util.EncryptorManager;
import unl.edu.ec.fieldPal.exception.EncryptorException;

@Named
@ViewScoped
public class ProfileBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AuthBean authBean;

    @Inject
    private UserService userService;

    // Usuario que se está editando en el formulario
    private User editingUser;

    // Campos temporales para el cambio de contraseña
    private String newPassword;
    private String confirmPassword;

    @PostConstruct
    public void init() {
        if (authBean.isAuthenticated()) {
            // Se clonan los datos para no afectar la sesión global hasta dar "Guardar"
            User current = authBean.getCurrentUser();
            this.editingUser = new User(
                    current.getId(),
                    current.getName(),
                    current.getEmail(),
                    current.getPhone(),
                    current.getPassword(),
                    current.getRole()
            );
        } else {
            // Nunca dejar editingUser en null: cualquier vista que la referencie
            // (aunque esté oculta) tiraría un error de EL sin sesión iniciada.
            this.editingUser = new User("", "", "", "", "", null);
        }
    }

    public void doUpdateProfile() {
        
        // 1. Verificaciones de Datos Válidos
        if (!validateData()) {
            return;
        }


        try {
            // 2. Lógica de Encriptación si hay nueva contraseña
            if (newPassword != null && !newPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no coinciden.");
                    return;
                }

                String encryptedPass = EncryptorManager.encrypt(newPassword);
                editingUser.setPassword(encryptedPass);
            }

            // Persistencia en el Servicio
            // Esto asegura que los cambios no se borren al recargar el servicio
            userService.updateUser(editingUser);

            // Sincronizar con la sesión actual para que el Header cambie el nombre al instante
            authBean.setCurrentUser(editingUser);

            addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tu perfil ha sido actualizado correctamente.");

            // Limpiar campos de contraseña para seguridad
            this.newPassword = null;
            this.confirmPassword = null;

        } catch (EncryptorException e) {

            addMessage(FacesMessage.SEVERITY_FATAL, "Error de Seguridad", "No se pudo procesar la contraseña: " + e.getMessage());
        }
    }

    private boolean validateData() {
        if (editingUser.getName() == null || editingUser.getName().trim().isEmpty()) {
            addMessage(FacesMessage.SEVERITY_WARN, "Validación", "El nombre es obligatorio.");
            return false;
        }
        if (editingUser.getEmail() == null || !editingUser.getEmail().contains("@")) {
            addMessage(FacesMessage.SEVERITY_WARN, "Validación", "Ingresa un correo electrónico válido.");
            return false;
        }
        return true;
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // --- Getters y Setters  ---
    public User getEditingUser() { return editingUser; }
    public void setEditingUser(User editingUser) { this.editingUser = editingUser; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}

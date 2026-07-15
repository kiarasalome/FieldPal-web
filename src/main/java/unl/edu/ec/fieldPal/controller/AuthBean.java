package unl.edu.ec.fieldPal.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import unl.edu.ec.fieldPal.service.UserService;

import java.io.Serial;
import java.io.Serializable;

@Named
@ViewScoped
public class AuthBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    // Estado del modal
    private boolean showAuthModal = false;
    private String authMode = "login"; // "login" o "register"

    // Campos del formulario
    private String loginEmail = "";
    private String loginPassword = "";
    private String registerName = "";
    private String registerEmail = "";
    private String registerPhone = "";
    private String registerPassword = "";
    private String registerRole = "PLAYER";

    // Usuario actual en sesión
    private User currentUser;

    // === Método de Login ===
    public String submitLogin() {
        User user = userService.login(loginEmail, loginPassword);
        if (user != null) {
            currentUser = user;
            showAuthModal = false;
            clearLoginForm();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "¡Bienvenido, " + user.getName() + "!", ""));

            // Retornamos navegación explícita con redirección limpia
            return user.isAdmin() ? "/admin/gestion.xhtml?faces-redirect=true"
                    : "/home.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Correo o contraseña incorrectos.", ""));
        return null;
    }

    // === Método de Registro ===
    public String submitRegister() {
        UserRole role = "ADMIN".equals(registerRole) ? UserRole.ADMIN : UserRole.PLAYER;
        User user = userService.register(registerName, registerEmail,
                registerPhone, registerPassword, role);
        if (user != null) {
            currentUser = user;
            showAuthModal = false;
            clearRegisterForm();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Cuenta creada exitosamente. ¡Bienvenido, " + user.getName() + "!", ""));

            return user.isAdmin() ? "/admin/gestion.xhtml?faces-redirect=true"
                    : "/home.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ya existe una cuenta con este correo electrónico.", ""));
        return null;
    }

    // === Métodos de sesión ===
    public String doLogout() {
        currentUser = null;
        return "/home.xhtml?faces-redirect=true";
    }

    // Alias para coincidir exactamente con el action de header.xhtml
    public String logout() {
        return doLogout();
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    public boolean isPlayer() {
        return currentUser != null && currentUser.isPlayer();
    }

    // === Modal control ===
    public void openLogin() {
        authMode = "login";
        showAuthModal = true;
    }

    public void openRegister() {
        authMode = "register";
        showAuthModal = true;
    }

    public void closeAuth() {
        showAuthModal = false;
        clearLoginForm();
        clearRegisterForm();
    }

    public void switchToRegister() {
        authMode = "register";
        clearLoginForm();
    }

    public void switchToLogin() {
        authMode = "login";
        clearRegisterForm();
    }

    // === Limpiar formularios ===
    private void clearLoginForm() {
        loginEmail = "";
        loginPassword = "";
    }

    private void clearRegisterForm() {
        registerName = "";
        registerEmail = "";
        registerPhone = "";
        registerPassword = "";
        registerRole = "PLAYER";
    }

    // === Getters y Setters ===
    public boolean isShowAuthModal() { return showAuthModal; }
    public void setShowAuthModal(boolean showAuthModal) { this.showAuthModal = showAuthModal; }

    public String getAuthMode() { return authMode; }
    public void setAuthMode(String authMode) { this.authMode = authMode; }

    public String getLoginEmail() { return loginEmail; }
    public void setLoginEmail(String loginEmail) { this.loginEmail = loginEmail; }

    public String getLoginPassword() { return loginPassword; }
    public void setLoginPassword(String loginPassword) { this.loginPassword = loginPassword; }

    public String getRegisterName() { return registerName; }
    public void setRegisterName(String registerName) { this.registerName = registerName; }

    public String getRegisterEmail() { return registerEmail; }
    public void setRegisterEmail(String registerEmail) { this.registerEmail = registerEmail; }

    public String getRegisterPhone() { return registerPhone; }
    public void setRegisterPhone(String registerPhone) { this.registerPhone = registerPhone; }

    public String getRegisterPassword() { return registerPassword; }
    public void setRegisterPassword(String registerPassword) { this.registerPassword = registerPassword; }

    public String getRegisterRole() { return registerRole; }
    public void setRegisterRole(String registerRole) { this.registerRole = registerRole; }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }
}

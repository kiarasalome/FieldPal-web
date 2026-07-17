package unl.edu.ec.fieldPal.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import unl.edu.ec.fieldPal.model.User;
import unl.edu.ec.fieldPal.model.enums.UserRole;
import unl.edu.ec.fieldPal.service.UserService;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    // Campos del formulario
    private String loginEmail = "";
    private String loginPassword = "";
    private String registerName = "";
    private String registerEmail = "";
    private String registerPhone = "";
    private String registerPassword = "";
    private String registerConfirmPassword = "";
    private String registerRole = "PLAYER";

    // === Validación ===
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    private static final int MIN_PASSWORD_LENGTH = 6;

    // Usuario actual en sesión
    private User currentUser;

    // === Método de Login ===
    public String submitLogin() {
        loginEmail = trim(loginEmail);

        if (isBlank(loginEmail) || isBlank(loginPassword)) {
            addError("Ingresa tu correo y contraseña.");
            return null;
        }

        User user = userService.login(loginEmail, loginPassword);
        if (user == null) {
            addError("Correo o contraseña incorrectos.");
            return null;
        }

        loginSuccess(user, "¡Bienvenido, " + user.getName() + "!");
        clearLoginForm();
        // Por ahora, tanto admin como jugador aterrizan en el inicio;
        // el menú superior ya se adapta según el rol (ver header.xhtml).
        return "/homepage.xhtml?faces-redirect=true";
    }

    // === Método de Registro ===
    public String submitRegister() {
        registerName = trim(registerName);
        registerEmail = trim(registerEmail);
        registerPhone = trim(registerPhone);

        if (isBlank(registerName) || registerName.length() < 2) {
            addError("Ingresa tu nombre completo.");
            return null;
        }
        if (isBlank(registerEmail) || !EMAIL_PATTERN.matcher(registerEmail).matches()) {
            addError("Ingresa un correo electrónico válido.");
            return null;
        }
        if (isBlank(registerPhone) || registerPhone.replaceAll("\\D", "").length() < 7) {
            addError("Ingresa un número de teléfono válido.");
            return null;
        }
        if (isBlank(registerPassword) || registerPassword.length() < MIN_PASSWORD_LENGTH) {
            addError("La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.");
            return null;
        }
        if (!registerPassword.equals(registerConfirmPassword)) {
            addError("Las contraseñas no coinciden.");
            return null;
        }

        UserRole role = "ADMIN".equals(registerRole) ? UserRole.ADMIN : UserRole.PLAYER;
        User user = userService.register(registerName, registerEmail,
                registerPhone, registerPassword, role);
        if (user == null) {
            addError("Ya existe una cuenta con este correo electrónico.");
            return null;
        }

        loginSuccess(user, "Cuenta creada exitosamente. ¡Bienvenido, " + user.getName() + "!");
        clearRegisterForm();
        return "/homepage.xhtml?faces-redirect=true";
    }

    // === Helpers de validación (evitan repetir la misma lógica en login/registro) ===
    private void loginSuccess(User user, String welcomeMessage) {
        currentUser = user;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, welcomeMessage, ""));
    }

    private void addError(String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, detail, ""));
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    // === Métodos de sesión ===
    public String doLogout() {
        currentUser = null;
        return "/homepage.xhtml?faces-redirect=true";
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
        registerConfirmPassword = "";
        registerRole = "PLAYER";
    }

    // === Getters y Setters ===
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

    public String getRegisterConfirmPassword() { return registerConfirmPassword; }
    public void setRegisterConfirmPassword(String registerConfirmPassword) { this.registerConfirmPassword = registerConfirmPassword; }

    public String getRegisterRole() { return registerRole; }
    public void setRegisterRole(String registerRole) { this.registerRole = registerRole; }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }
}

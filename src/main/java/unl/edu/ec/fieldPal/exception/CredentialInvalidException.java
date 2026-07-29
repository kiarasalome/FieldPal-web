package unl.edu.ec.fieldPal.exception;

public class CredentialInvalidException extends Exception{

    public CredentialInvalidException() {
        super("Credenciales invalidas");
    }

    public CredentialInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}

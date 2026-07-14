package unl.edu.ec.fieldPal.exception;

public class AlreadyEntityException extends Exception{

    public AlreadyEntityException() {
        this("Entity already exists");
    }

    public AlreadyEntityException(String message) {
        super(message);
    }

    public AlreadyEntityException(String message, Throwable cause) {
        super(message, cause);
    }


}

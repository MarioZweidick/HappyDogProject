package at.happydog.test.exception.custom;

public class AppUserException extends RuntimeException{
    private String errorMessage;

    public AppUserException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

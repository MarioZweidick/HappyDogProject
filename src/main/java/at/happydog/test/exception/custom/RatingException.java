package at.happydog.test.exception.custom;

public class RatingException extends RuntimeException{
    private String errorMessage;

    public RatingException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

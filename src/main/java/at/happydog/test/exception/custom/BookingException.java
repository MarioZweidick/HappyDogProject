package at.happydog.test.exception.custom;

public class BookingException extends RuntimeException{


    private String errorMessage;

    public BookingException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

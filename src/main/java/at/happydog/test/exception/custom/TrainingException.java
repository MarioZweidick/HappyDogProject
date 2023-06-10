package at.happydog.test.exception.custom;

public class TrainingException extends RuntimeException{


    private String errorMessage;

    public TrainingException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

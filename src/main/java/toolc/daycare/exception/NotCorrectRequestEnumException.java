package toolc.daycare.exception;

public class NotCorrectRequestEnumException extends RuntimeException{
    private static final String MESSAGE = "열거형 값이 잘못되었습니다.";

    public NotCorrectRequestEnumException() {
        super(MESSAGE);
    }

    public static String getErrorMessage() {
        return MESSAGE;
    }
}

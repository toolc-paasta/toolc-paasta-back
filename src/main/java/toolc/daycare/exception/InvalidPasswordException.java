package toolc.daycare.exception;

public class InvalidPasswordException extends RuntimeException{
    private static final String MESSAGE = "비밀번호가 틀렸습니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    public static String getErrorMessage() {
        return MESSAGE;
    }
}

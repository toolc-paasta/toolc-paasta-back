package toolc.daycare.exception;

public class NotExistMemberException extends RuntimeException{
    private static final String MESSAGE = "없는 사용자 입니다.";

    public NotExistMemberException() {
        super(MESSAGE);
    }

    public static String getErrorMessage() {
        return MESSAGE;
    }
}

package toolc.daycare.exception;

public class AlreadyMatchCenterException extends RuntimeException{
    private static final String MESSAGE = "원장이 이미 등록된 어린이집이 있습니다.";

    public AlreadyMatchCenterException() {
        super(MESSAGE);
    }

    public static String getErrorMessage() {
        return MESSAGE;
    }
}

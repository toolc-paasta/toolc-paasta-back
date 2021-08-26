package toolc.daycare.exception;

public class DuplicateMemberException extends RuntimeException{
    private static final String MESSAGE = "ID가 중복된 회원입니다.";

    public DuplicateMemberException() {
        super(MESSAGE);
    }

    public static String getErrorMessage() {
        return MESSAGE;
    }
}

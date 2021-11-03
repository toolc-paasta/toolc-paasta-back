package toolc.daycare.exception;

public class NoExistCenterException extends RuntimeException {
  private static final String MESSAGE = "원장의 어린이집이 없습니다.";

  public NoExistCenterException() {
    super(MESSAGE);
  }

  public static String getErrorMessage() {
    return MESSAGE;
  }
}

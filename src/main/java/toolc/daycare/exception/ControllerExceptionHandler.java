package toolc.daycare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toolc.daycare.dto.BaseResponseFailDto;
import toolc.daycare.dto.error.BadRequestFailResponseDto;
import toolc.daycare.dto.error.ServerErrorFailResponseDto;

@RestControllerAdvice
public class ControllerExceptionHandler {
  @ExceptionHandler(value = {
    NotExistRequestValueException.class,
    DuplicateMemberException.class,
    NoExistCenterException.class,
    NotCorrectRequestEnumException.class,
    InvalidPasswordException.class,
    NotExistMemberException.class,
    AlreadyMatchCenterException.class
  })
  public ResponseEntity<?> badRequest(Exception e) {
    BaseResponseFailDto responseBody = BadRequestFailResponseDto.builder()
      .status(HttpStatus.BAD_REQUEST.value())
      .message(e.getMessage())
      .build();

    return ResponseEntity.badRequest().body(responseBody);
  }

  @ExceptionHandler({
    RuntimeException.class
  })
  public ResponseEntity<?> serverError(Exception e) {
    BaseResponseFailDto responseBody = ServerErrorFailResponseDto.builder()
      .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
      .message(e.getMessage())
      .build();

    return ResponseEntity.internalServerError().body(responseBody);
  }
}

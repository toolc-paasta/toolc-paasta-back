package toolc.daycare.dto.member.request.teacher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterClassRequestDto {

  private String centerName;
  private String className;

  public RegisterClassRequestDto(String centerName, String className) {
    this.centerName = centerName;
    this.className = className;
  }
}
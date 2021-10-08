package toolc.daycare.dto.member.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDto {

  private String loginId;
  private String password;
  private String expoToken;

  @Builder
  public LoginRequestDto(String loginId, String password, String expoToken) {
    this.loginId = loginId;
    this.password = password;
    this.expoToken = expoToken;
  }

}

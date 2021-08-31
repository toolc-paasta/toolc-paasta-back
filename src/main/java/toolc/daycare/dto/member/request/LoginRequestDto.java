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

    @Builder
    public LoginRequestDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

}

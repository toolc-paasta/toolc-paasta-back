package toolc.daycare.dto.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String loginId;
    private String password;

    @Builder
    public LoginRequestDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

}

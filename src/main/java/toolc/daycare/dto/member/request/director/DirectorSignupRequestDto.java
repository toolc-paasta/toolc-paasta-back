package toolc.daycare.dto.member.request.director;

import lombok.Builder;
import lombok.Getter;
import toolc.daycare.domain.member.Sex;

@Getter
public class DirectorSignupRequestDto {

    private String loginId;
    private String password;
    private String name;
    private Sex sex;

    @Builder
    public DirectorSignupRequestDto(String loginId, String password, String name, Sex sex) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.sex = sex;
    }

}

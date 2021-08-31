package toolc.daycare.dto.member.request.teacher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.member.Sex;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherSignupRequestDto {

    private String loginId;
    private String password;
    private String name;
    private Sex sex;

    @Builder
    public TeacherSignupRequestDto(String loginId, String password, String name, Sex sex) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.sex = sex;
    }
}
package toolc.daycare.dto.member.request.director;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.member.Sex;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectorSignupRequestDto {

    private String loginId;
    private String password;
    private String name;
    private String connectionNumber;
    private Sex sex;

    @Builder
    public DirectorSignupRequestDto(String loginId, String password, String name, String connectionNumber, Sex sex) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.connectionNumber = connectionNumber;
        this.sex = sex;
    }
}

package toolc.daycare.dto.member.response.teacher;

import lombok.Getter;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class TeacherSignupResponseDto extends BaseResponseSuccessDto {

    private final Teacher response;

    public TeacherSignupResponseDto(Teacher response) {
        this.response = response;
    }

}

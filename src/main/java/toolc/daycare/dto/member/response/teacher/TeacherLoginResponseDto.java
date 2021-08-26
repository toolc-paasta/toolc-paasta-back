package toolc.daycare.dto.member.response.teacher;

import lombok.Getter;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class TeacherLoginResponseDto extends BaseResponseSuccessDto {

    private final Teacher response;

    public TeacherLoginResponseDto(Teacher response) {
        this.response = response;
    }

}

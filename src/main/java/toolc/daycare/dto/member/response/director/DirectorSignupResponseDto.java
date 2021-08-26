package toolc.daycare.dto.member.response.director;

import lombok.Getter;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class DirectorSignupResponseDto extends BaseResponseSuccessDto {

    private final Director response;

    public DirectorSignupResponseDto(Director response) {
        this.response = response;
    }

}

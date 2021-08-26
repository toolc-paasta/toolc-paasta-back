package toolc.daycare.dto.member.response.director;

import lombok.Getter;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class DirectorLoginResponseDto extends BaseResponseSuccessDto {

    private final Director response;

    public DirectorLoginResponseDto(Director response) {
        this.response = response;
    }

}

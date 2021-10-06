package toolc.daycare.dto.member.response.director;

import lombok.Getter;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.authentication.TokenVO;

@Getter
public class DirectorLoginResponseDto extends BaseResponseSuccessDto {

    private final TokenVO response;

    public DirectorLoginResponseDto(TokenVO response) {
        this.response = response;
    }

}

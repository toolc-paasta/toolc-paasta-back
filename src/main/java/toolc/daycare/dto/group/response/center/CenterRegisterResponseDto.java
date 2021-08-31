package toolc.daycare.dto.group.response.center;

import lombok.Getter;
import toolc.daycare.domain.group.Center;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class CenterRegisterResponseDto extends BaseResponseSuccessDto {

    private final Center response;

    public CenterRegisterResponseDto(Center response) {
        this.response = response;
    }

}

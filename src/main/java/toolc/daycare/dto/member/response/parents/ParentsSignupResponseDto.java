package toolc.daycare.dto.member.response.parents;

import lombok.Getter;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class ParentsSignupResponseDto extends BaseResponseSuccessDto {

    private final Parents response;

    public ParentsSignupResponseDto(Parents response) {
        this.response = response;
    }

}

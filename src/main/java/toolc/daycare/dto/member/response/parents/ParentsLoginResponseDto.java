package toolc.daycare.dto.member.response.parents;

import lombok.Getter;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class ParentsLoginResponseDto extends BaseResponseSuccessDto {

    private final Parents response;

    public ParentsLoginResponseDto(Parents response) {
        this.response = response;
    }

}

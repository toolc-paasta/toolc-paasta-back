package toolc.daycare.dto.member.response.admin;

import lombok.Getter;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.dto.BaseResponseSuccessDto;

@Getter
public class AdminLoginResponseDto extends BaseResponseSuccessDto {

    private final Admin response;

    public AdminLoginResponseDto(Admin response) {
        this.response = response;
    }

}

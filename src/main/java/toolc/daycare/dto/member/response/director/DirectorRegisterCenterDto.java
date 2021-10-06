package toolc.daycare.dto.member.response.director;

import lombok.Getter;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.service.fcm.FcmSendBody;

import java.util.List;

@Getter
public class DirectorRegisterCenterDto extends BaseResponseSuccessDto {

    private final FcmSendBody response;

    public DirectorRegisterCenterDto(FcmSendBody response) {
        this.response = response;
    }

}

package toolc.daycare.dto.group.request.center;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CenterRegisterRequestDto {

    private String directorLoginId;
    private String centerName;
    private String address;
    private LocalDate foundationDate;


    //TODO:  사업자 등록증 이미지 받기 필요


    @Builder
    public CenterRegisterRequestDto(String directorLoginId, String centerName, String address, LocalDate foundationDate) {
        this.directorLoginId = directorLoginId;
        this.centerName = centerName;
        this.address = address;
        this.foundationDate = foundationDate;
    }
}

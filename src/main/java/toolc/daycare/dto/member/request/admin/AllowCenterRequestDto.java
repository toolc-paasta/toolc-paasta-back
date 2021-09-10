package toolc.daycare.dto.member.request.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllowCenterRequestDto {

    //admin 계정 id -> authentication 으로 교체 예정
    private String loginId;

    private String DirectorLoginId;
    private String centerName;
    private String address;
    private LocalDate foundationDate;

    
    //TODO : 유치원 인증 사진 받아야함 - 사업자 등록 등
    //인증 사진
//    private
}

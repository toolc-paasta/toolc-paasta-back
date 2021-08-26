package toolc.daycare.dto.member.request.parents;

import lombok.Builder;
import lombok.Getter;
import toolc.daycare.domain.member.Sex;

import java.util.Date;

@Getter
public class ParentsSignupRequestDto {

    //본인 정보
    private String loginId;
    private String password;
    private String name;
    private Sex sex;

    //자녀 정보
    private String childName;
    private Date childBirthday;
    private Sex childSex;

    @Builder
    public ParentsSignupRequestDto(String loginId, String password, String name, Sex sex,
                                   String childName, Date childBirthday, Sex childSex) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.childName = childName;
        this.childBirthday = childBirthday;
        this.childSex = childSex;
    }
}
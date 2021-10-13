package toolc.daycare.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;

import javax.persistence.*;

@Entity(name = "MEMBER")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBaseEntity extends BaseEntity {

    //계정 정보
    private String loginId;
    @JsonIgnore
    private String password;

    private String name;
    private String connectionNumber;
    private String expoToken;
    @Enumerated(value = EnumType.STRING)
    private Sex sex;


    public MemberBaseEntity(String loginId, String password, String name, String connectionNumber, String expoToken, Sex sex) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.connectionNumber = connectionNumber;
        this.expoToken = expoToken;
        this.sex = sex;
    }

    //TODO : 토큰 받아서 처리해줘야함
    //토큰 따로 발급 방법 안정해서 임시로 해놓음
    public void setExpoToken(String token){
        this.expoToken = token;
    }
}

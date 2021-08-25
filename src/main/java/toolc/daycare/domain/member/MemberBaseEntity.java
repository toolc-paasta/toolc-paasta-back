package toolc.daycare.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
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
    @Enumerated(value = EnumType.STRING)
    private Sex sex;


    public MemberBaseEntity(String loginId, String password, String name, String connectionNumber, Sex sex) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.connectionNumber = connectionNumber;
        this.sex = sex;
    }
}

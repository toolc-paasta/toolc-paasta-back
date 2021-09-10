package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends MemberBaseEntity{

    @Builder
    public Admin(String loginId, String password, String name, String connectionNumber, String token, Sex sex) {
        super(loginId, password, name, connectionNumber, token, sex);
    }
}

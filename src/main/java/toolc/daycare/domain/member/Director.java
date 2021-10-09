package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.group.Center;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import static toolc.daycare.domain.member.Authority.DIRECTOR;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director extends MemberBaseEntity{

    @OneToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @Builder
    public Director(String loginId, String password, String name, String connectionNumber, String token, Sex sex) {
        super(loginId, password, name, connectionNumber, token, sex, DIRECTOR);
    }




}

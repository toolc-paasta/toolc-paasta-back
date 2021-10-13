package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.group.Class;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends MemberBaseEntity {

    private String role;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;

    @Builder
    public Teacher(String loginId, String password, String name, String connectionNumber,
                   String expoToken, Sex sex, String role) {
        super(loginId, password, name, connectionNumber, expoToken, sex);
        this.role = role;
    }
}

package toolc.daycare.domain.member;

import lombok.*;
import toolc.daycare.domain.group.Class;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static toolc.daycare.domain.member.Authority.TEACHER;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends MemberBaseEntity {

    @ManyToOne
    @JoinColumn(name = "class_id")
    @Setter
    private Class aClass;

    @Builder
    public Teacher(String loginId, String password, String name, String connectionNumber, String token, Sex sex) {
        super(loginId, password, name, connectionNumber, token, sex, TEACHER);
    }

}

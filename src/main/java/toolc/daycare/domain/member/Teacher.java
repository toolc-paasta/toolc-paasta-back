package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.group.Class;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static toolc.daycare.domain.member.Authority.TEACHER;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends MemberBaseEntity {

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;

    @Builder

    public Teacher(String loginId, String password, String name, String connectionNumber, String token, Sex sex) {
        super(loginId, password, name, connectionNumber, token, sex, TEACHER);
    }
}

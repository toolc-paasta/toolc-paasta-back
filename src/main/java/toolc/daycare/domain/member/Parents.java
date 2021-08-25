package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parents extends MemberBaseEntity{

    private Boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Builder

    public Parents(String loginId, String password, String name, String connectionNumber, Sex sex, Boolean isPrimary) {
        super(loginId, password, name, connectionNumber, sex);
        this.isPrimary = isPrimary;
    }

    public void setStudents(Student student){
        this.student = student;
    }
}

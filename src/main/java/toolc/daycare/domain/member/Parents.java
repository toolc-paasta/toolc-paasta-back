package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static toolc.daycare.domain.member.Authority.PARENT;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parents extends MemberBaseEntity{

    private String childName;

//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate childBirthday;

    @Enumerated(value = EnumType.STRING)
    private Sex childSex;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Builder
    public Parents(String loginId, String password, String name, String connectionNumber, String token, Sex sex,
                   String childName, LocalDate childBirthday, Sex childSex) {
        super(loginId, password, name, connectionNumber, token, sex, PARENT);
        this.childName = childName;
        this.childBirthday = childBirthday;
        this.childSex = childSex;
    }


    public void setStudents(Student student){
        this.student = student;
    }
}

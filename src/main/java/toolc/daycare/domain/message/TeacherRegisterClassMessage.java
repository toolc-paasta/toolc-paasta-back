package toolc.daycare.domain.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherRegisterClassMessage extends BaseEntity {

  @OneToOne
  private Teacher teacher;

  private Long centerId;

  private Long classId;

  public TeacherRegisterClassMessage(Teacher teacher, Long centerId, Long classId) {
    this.teacher = teacher;
    this.centerId = centerId;
    this.classId = classId;
  }
}

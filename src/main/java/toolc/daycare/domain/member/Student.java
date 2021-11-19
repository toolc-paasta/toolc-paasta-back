package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.group.Area;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.group.Shuttle;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends BaseEntity {

  private String name;
  private LocalDate birthday;
//    private String connectionNumber;

  @Enumerated(value = EnumType.STRING)
  private Sex sex;


  @ManyToOne
  @JoinColumn(name = "class_id")
  private Class aClass;

  @ManyToOne
  @JoinColumn(name = "area_id")
  private Area area;

  @ManyToOne
  @JoinColumn(name = "shuttle_id")
  private Shuttle shuttle;


  @Builder
  public Student(String name, LocalDate birthday, Sex sex) {
    this.name = name;
    this.birthday = birthday;
//        this.connectionNumber = connectionNumber;
    this.sex = sex;
  }

  public void setaClass(Class aClass) {
    this.aClass = aClass;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public void setShuttle(Shuttle shuttle) {
    this.shuttle = shuttle;
  }
}

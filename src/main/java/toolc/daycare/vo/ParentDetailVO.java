package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;
import toolc.daycare.domain.member.Authority;
import toolc.daycare.domain.member.Sex;

import java.time.LocalDate;

import static toolc.daycare.domain.member.Authority.PARENT;

@Value
public class ParentDetailVO {
  String loginId;
  String name;
  String connectionNumber;
  Sex sex;
  String childName;
  Long childId;
  Sex childSex;
  LocalDate childBirthday;
  String directorLoginId;
  String teacherLoginId;
  String className;
  String centerName;
  Authority authority = PARENT;

  @Builder
  public ParentDetailVO(
    String loginId,
    String name,
    String connectionNumber,
    Sex sex,
    String childName,
    Long childId,
    Sex childSex,
    LocalDate childBirthday,
    String directorLoginId,
    String teacherLoginId, String className,
    String centerName) {
    this.loginId = loginId;
    this.name = name;
    this.connectionNumber = connectionNumber;
    this.sex = sex;
    this.childName = childName;
    this.childId = childId;
    this.childSex = childSex;
    this.childBirthday = childBirthday;
    this.directorLoginId = directorLoginId;
    this.teacherLoginId = teacherLoginId;
    this.className = className;
    this.centerName = centerName;
  }
}

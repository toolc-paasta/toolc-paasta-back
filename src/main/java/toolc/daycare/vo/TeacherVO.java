package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;
import toolc.daycare.domain.member.Authority;
import toolc.daycare.domain.member.Sex;

import static toolc.daycare.domain.member.Authority.TEACHER;

@Value
public class TeacherVO {
  String loginId;
  String name;
  String connectionNumber;
  String token;
  Sex sex;
  String directorLoginId;
  String className;
  Long classId;
  String centerName;
  Authority authority = TEACHER;

  @Builder
  public TeacherVO(
    String loginId,
    String name,
    String connectionNumber,
    String token,
    Sex sex,
    String directorLoginId,
    String className,
    Long classId, String centerName) {
    this.loginId = loginId;
    this.name = name;
    this.connectionNumber = connectionNumber;
    this.token = token;
    this.sex = sex;
    this.directorLoginId = directorLoginId;
    this.className = className;
    this.classId = classId;
    this.centerName = centerName;
  }
}

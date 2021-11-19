package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;
import toolc.daycare.domain.member.Authority;
import toolc.daycare.domain.member.Sex;

import java.time.LocalDate;

import static toolc.daycare.domain.member.Authority.PARENT;

@Value
public class ParentVO {
  String loginId;
  String name;
  String connectionNumber;
  Sex sex;
  String childName;
  Long childId;
  Sex childSex;
  LocalDate childBirthday;
  Authority authority = PARENT;

  @Builder
  public ParentVO(
    String loginId,
    String name,
    String connectionNumber,
    Sex sex,
    String childName,
    Long childId,
    Sex childSex,
    LocalDate childBirthday) {
    this.loginId = loginId;
    this.name = name;
    this.connectionNumber = connectionNumber;
    this.sex = sex;
    this.childName = childName;
    this.childId = childId;
    this.childSex = childSex;
    this.childBirthday = childBirthday;
  }
}

package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;
import toolc.daycare.domain.member.Sex;

import java.time.LocalDate;

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

  @Builder
  public ParentVO(String loginId, String name, String connectionNumber, Sex sex, String childName, Long childId, Sex childSex, LocalDate childBirthday) {
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

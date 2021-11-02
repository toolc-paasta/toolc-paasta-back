package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;
import toolc.daycare.domain.member.Sex;

@Value
public class ParentVO {
  String loginId;
  String name;
  Sex sex;
  String childName;
  Long childId;
  Sex childSex;

  @Builder
  public ParentVO(String loginId, String name, Sex sex, String childName, Long childId, Sex childSex) {
    this.loginId = loginId;
    this.name = name;
    this.sex = sex;
    this.childName = childName;
    this.childId = childId;
    this.childSex = childSex;
  }
}

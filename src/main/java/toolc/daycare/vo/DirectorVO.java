package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;
import toolc.daycare.domain.member.Authority;
import toolc.daycare.domain.member.Sex;

import static toolc.daycare.domain.member.Authority.DIRECTOR;

@Value
public class DirectorVO {
  String loginId;
  String name;
  String connectionNumber;
  String token;
  Sex sex;
  Authority authority = DIRECTOR;
  String centerName;
  Long centerId;

  @Builder
  public DirectorVO(String loginId, String name, String connectionNumber, String token, Sex sex, String centerName, Long centerId) {
    this.loginId = loginId;
    this.name = name;
    this.connectionNumber = connectionNumber;
    this.token = token;
    this.sex = sex;
    this.centerName = centerName;
    this.centerId = centerId;
  }
}

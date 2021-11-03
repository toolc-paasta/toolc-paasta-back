package toolc.daycare.dto.member.request.parents;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.member.Sex;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParentsSignupRequestDto {

  //본인 정보
  private String loginId;
  private String password;
  private String name;
  private String connectionNumber;
  private Sex sex;

  //자녀 정보
  private String childName;
  private LocalDate childBirthday;
  private Sex childSex;

  @Builder
  public ParentsSignupRequestDto(String loginId, String password, String name, String connectionNumber,
                                 Sex sex, String childName, LocalDate childBirthday, Sex childSex) {
    this.loginId = loginId;
    this.password = password;
    this.name = name;
    this.connectionNumber = connectionNumber;
    this.sex = sex;
    this.childName = childName;
    this.childBirthday = childBirthday;
    this.childSex = childSex;
  }
}
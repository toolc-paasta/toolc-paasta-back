package toolc.daycare.domain.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.member.Director;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CenterRegisterMessage extends BaseEntity {

  @OneToOne
  private Director director;

  private String centerName;

  private String address;

  private LocalDate foundationDate;

  public CenterRegisterMessage(Director director, String centerName, String address, LocalDate foundationDate) {
    this.director = director;
    this.centerName = centerName;
    this.address = address;
    this.foundationDate = foundationDate;
  }
}

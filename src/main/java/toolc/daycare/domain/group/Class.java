package toolc.daycare.domain.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.member.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Class extends BaseEntity {

  private String name;


  @ManyToOne
  @JoinColumn(name = "center_id")
  private Center center;

  @Builder
  public Class(String name) {
    this.name = name;
  }

  public void setCenter(Center center) {
    this.center = center;
  }
}

package toolc.daycare.domain.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Teacher;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Center extends BaseEntity {

  private String name;
  private String address;
  private String channelName;

  //    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate foundationDate;

  private Long star = 0L;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "director_id")
  private Director director;


  @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
  private List<Class> classes = new ArrayList<>();


  @Builder
  public Center(String name, String address, LocalDate foundationDate, Long star, String channelName) {
    this.name = name;
    this.address = address;
    this.foundationDate = foundationDate;
    this.star = star;
    this.channelName = channelName;
  }

  public void setDirector(Director director) {
    this.director = director;
  }
}

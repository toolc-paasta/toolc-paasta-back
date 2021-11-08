package toolc.daycare.domain.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

  private String title;

  private String content;

  private LocalDate dateTime;

  private String author;

  private String img;

  @ManyToOne
  @JoinColumn(name = "center_id")
  private Center center;

  @Builder
  public Notice(String title, String content, LocalDate dateTime, String author, String img, Center center) {
    this.title = title;
    this.content = content;
    this.dateTime = dateTime;
    this.author = author;
    this.img = img;
    this.center = center;
  }

}

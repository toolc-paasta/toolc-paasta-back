package toolc.daycare.dto.member.request.teacher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeRequestDto {

  private String title;
  private String content;
  private String img;

  public NoticeRequestDto(String title, String content, String img) {
    this.title = title;
    this.content = content;
    this.img = img;
  }
}

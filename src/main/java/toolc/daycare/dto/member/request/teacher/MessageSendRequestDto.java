package toolc.daycare.dto.member.request.teacher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageSendRequestDto {

    private String centerName;
    private String className;
    private String title;
    private String body;

  public MessageSendRequestDto(String centerName, String className, String title, String body) {
    this.centerName = centerName;
    this.className = className;
    this.title = title;
    this.body = body;
  }
}

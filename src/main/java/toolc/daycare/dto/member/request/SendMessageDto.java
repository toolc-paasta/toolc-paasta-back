package toolc.daycare.dto.member.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SendMessageDto {
  String title;
  String body;
  String targetLoginId;

  @Builder
  public SendMessageDto(String title, String body, String targetLoginId) {
    this.title = title;
    this.body = body;
    this.targetLoginId = targetLoginId;
  }
}

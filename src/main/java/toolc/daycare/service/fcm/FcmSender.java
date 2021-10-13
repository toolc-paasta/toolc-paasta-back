package toolc.daycare.service.fcm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import toolc.daycare.fcm.FcmWebClient;
import toolc.daycare.repository.interfaces.member.MemberRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FcmSender {

  private FcmWebClient fcmWebClient;
  private MemberRepository memberRepository;

  @Autowired
  public FcmSender(FcmWebClient fcmWebClient,
                   MemberRepository memberRepository) {
    this.fcmWebClient = fcmWebClient;
    this.memberRepository = memberRepository;
  }

  public FcmSendBody sendFcmJson(/*String senderName, */String title, String body, List<String> targetUser, Map<String, Object> data) {

    List<String> targetTokens = new LinkedList<>();
    for (String userId : targetUser) {
      targetTokens.add(memberRepository.findByLoginId(userId).get().getExpoToken());
    }

    FcmSendBody fcmSendBody = FcmSendBody.builder()
      .title(title)
      .body(body)
      .tokens(targetTokens)
      .data(data)
      .build();

    Mono<String> mono = fcmWebClient.webClient().post()
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(fcmSendBody)
      .retrieve()
      .bodyToMono(String.class);

    log.info("mono = {}", mono.block());
    return fcmSendBody;
  }
}

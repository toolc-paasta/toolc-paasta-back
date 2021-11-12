package toolc.daycare.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.authentication.Auth;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.member.request.SendMessageDto;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.fcm.FcmSender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
  private final FcmSender fcmSender;

  @PostMapping("/sendMessage")
  public ResponseEntity<?> sendMessage(@Auth String loginId, @RequestBody SendMessageDto dto) {

    List<String> targetUser = new LinkedList<>();
    targetUser.add(dto.getTargetLoginId());

    Map<String, Object> data = new HashMap<>();
    data.put("temp", "temp");

    FcmSendBody fcm = fcmSender.sendFcmJson(dto.getTitle(), dto.getBody(), targetUser, data);

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(
      OK.value(), "1 대 1 noticifaion 완료", fcm);
    return ResponseEntity.ok(responseBody);
  }
}

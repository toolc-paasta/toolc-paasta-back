package toolc.daycare.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.exception.AlreadyMatchCenterException;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;
import toolc.daycare.service.fcm.FcmSender;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

  private final MemberService memberService;
  private final AdminRepository adminRepository;
  private final CenterRepository centerRepository;
  private final CenterRegisterRepository registerRepository;
  private final TokenService tokenService;
  private final FcmSender fcmSender;


  public TokenVO login(String loginId, String password, String expoToken) {
      Admin admin = findAdminByLoginId(loginId);
      memberService.checkLoginPassword(admin, password);

      admin.setExpoToken(expoToken);

      AccessToken accessToken = tokenService.create(loginId, admin.getAuthority());

      return tokenService.formatting(accessToken);
  }
  
  public List<CenterRegisterMessage> getAllRegister() {
    List<CenterRegisterMessage> registers = registerRepository.findAll();

    return registers;

  }

  public Center allowRegister(Long messageId) {
    CenterRegisterMessage allow = registerRepository.findById(messageId).get();
    Center center = Center.builder()
      .name(allow.getCenterName())
      .address(allow.getAddress())
      .foundationDate(allow.getFoundationDate())
      .star(0L)
      .build();
    checkNotExistCenter(allow.getDirector());
    center.setDirector(allow.getDirector());

    List<String> targetUser = new LinkedList<>();
    targetUser.add(allow.getDirector().getLoginId());

    Map<String, Object> data = new HashMap<>();
    data.put("centerName", allow.getCenterName());
    data.put("address", allow.getAddress());
    data.put("foundationDate", allow.getFoundationDate());

    fcmSender.sendFcmJson("유치원 등록 수락 되었습니다."
      , center.getName() + "등록 요청이 수락 되었습니다.", targetUser, data);

    registerRepository.deleteById(messageId);

    return centerRepository.save(center);
  }

  public void rejectRegister(Long messageId) {
    CenterRegisterMessage reject = registerRepository.findById(messageId).get();

    List<String> targetUser = new LinkedList<>();
    targetUser.add(reject.getDirector().getLoginId());

    Map<String, Object> data = new HashMap<>();
    data.put("centerName", reject.getCenterName());

    fcmSender.sendFcmJson("유치원 등록 거절 되었습니다."
      , reject.getCenterName() + "등록 요청이 거절 되었습니다.", targetUser, data);

    registerRepository.deleteById(messageId);
  }

  private void checkNotExistCenter(Director director) {
    if (centerRepository.findByDirectorId(director.getId()) != null) {
      throw new AlreadyMatchCenterException();
    }
  }
  public Admin findAdminByLoginId(String loginId) {
    return adminRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
  }


}

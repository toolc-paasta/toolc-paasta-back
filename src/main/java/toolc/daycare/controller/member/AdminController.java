package toolc.daycare.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.group.request.center.CenterRegisterRequestDto;

import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.AdminService;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.CenterVO;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member/admin")
public class AdminController {
  private final AdminService adminService;
  private final DirectorService directorService;
  private final CenterService centerService;


  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Admin admin = adminService.findAdminByLoginId(loginId);

    ResponseDto<Admin> response = new ResponseDto<>(OK.value(), "정보 조회 성공", admin);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    RequestUtil.checkNeedValue(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword()
    );

    TokenVO token = adminService.login(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword(),
      loginRequestDto.getExpoToken()
    );

    ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "로그인 성공", token);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("allowCenter/{messageId}")
  public ResponseEntity<?> allowCenter(@Auth String loginId, @PathVariable Long messageId) {

    //TODO : 나중에 권한으로 바꿔줘야함
    Admin admin = adminService.findAdminByLoginId(loginId);

    Center newCenter = adminService.allowRegister(messageId);

    ResponseDto<Center> responseBody =
      new ResponseDto<>(OK.value(), "Center 등록 요청 수락 완료", newCenter);

    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("rejectCenter/{messageId}")
  public ResponseEntity<?> rejectCenter(@Auth String loginId, @PathVariable Long messageId) {

    //TODO : 나중에 권한으로 바꿔줘야함
    Admin admin = adminService.findAdminByLoginId(loginId);

    adminService.rejectRegister(messageId);

    ResponseDto<?> responseBody =
      new ResponseDto<>(OK.value(), "Center 등록 요청 거절 완료", null);

    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/registers")
  public ResponseEntity<?> getRegisters() {

    List<CenterRegisterMessage> registers = adminService.getAllRegister();

    ResponseDto<List<CenterRegisterMessage>> responseBody =
      new ResponseDto<>(OK.value(), "모든 요청 조회", registers);

    return ResponseEntity.ok(responseBody);
  }
}

package toolc.daycare.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.group.request.Class.CreateClassRequestDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.director.DirectorRegisterCenterRequestDto;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.dto.member.response.director.DirectorRegisterCenterDto;
import toolc.daycare.dto.member.response.director.DirectorSignupResponseDto;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.ClassVO;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/director")
public class DirectorController {

  private final DirectorService directorService;
  private final CenterService centerService;

  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Director director = directorService.findDirectorByLoginId(loginId);

    ResponseDto<Director> response = new ResponseDto<>(OK.value(), "정보 조회 성공", director);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody DirectorSignupRequestDto directorSignupRequestDto) {
    RequestUtil.checkNeedValue(
      directorSignupRequestDto.getLoginId(),
      directorSignupRequestDto.getPassword(),
      directorSignupRequestDto.getName(),
      directorSignupRequestDto.getConnectionNumber()
    );
    RequestUtil.checkCorrectEnum(
      directorSignupRequestDto.getSex()
    );


    Director newDirector = directorService.signUp(
      directorSignupRequestDto.getLoginId(),
      directorSignupRequestDto.getPassword(),
      directorSignupRequestDto.getName(),
      directorSignupRequestDto.getConnectionNumber(),
      directorSignupRequestDto.getSex()
    );

    ResponseDto<Director> responseBody = new ResponseDto<>(OK.value(), "회원가입 성공", newDirector);
    return new ResponseEntity<>(responseBody, CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    RequestUtil.checkNeedValue(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword()
    );

    TokenVO token = directorService.login(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword(),
      loginRequestDto.getExpoToken()
    );

    ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "로그인 성공", token);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/registerCenter")
  public ResponseEntity<?> DirectorRegisterCenter(@Auth String loginId, @RequestBody DirectorRegisterCenterRequestDto directorRegisterCenterRequestDto) {
    log.info("loginId = {}", loginId);
    RequestUtil.checkNeedValue(
      directorRegisterCenterRequestDto.getCenterName(),
      directorRegisterCenterRequestDto.getAddress(),
      directorRegisterCenterRequestDto.getFoundationDate()
    );

    FcmSendBody fcm = directorService.centerRegister(loginId,
      directorRegisterCenterRequestDto.getCenterName(),
      directorRegisterCenterRequestDto.getAddress(),
      directorRegisterCenterRequestDto.getFoundationDate());

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "요청 전송 성공", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/create/class")
  public ResponseEntity<?> createClass(@Auth String loginId, @RequestBody CreateClassRequestDto createClassRequestDto) {
    log.info("loginId = {}", loginId);
    RequestUtil.checkNeedValue(
      createClassRequestDto.getName()
    );

    Director director = directorService.findDirectorByLoginId(loginId);
    if (director.getCenter() == null) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "원장의 유치원이 없습니다.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    ClassVO classVO = centerService.createClass(director.getCenter(), createClassRequestDto.getName());
    ResponseDto<ClassVO> responseBody = new ResponseDto<>(OK.value(), "생성 성공", classVO);
    return ResponseEntity.ok(responseBody);
  }
}

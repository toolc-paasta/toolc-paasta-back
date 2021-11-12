package toolc.daycare.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.group.request.Class.CreateClassRequestDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.director.DirectorRegisterCenterRequestDto;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.mapper.DirectorMapper;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.ClassVO;
import toolc.daycare.vo.DirectorVO;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/director")
public class DirectorController {

  private final DirectorService directorService;
  private final CenterService centerService;
  private final DirectorMapper mapper = new DirectorMapper();

  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Director director = directorService.findDirectorByLoginId(loginId);
    DirectorVO directorVO;
    Optional<Center> centerOptional = centerService.findCenter(director.getId());

    if (centerOptional.isPresent()) {
      directorVO = mapper.toDirectorVOWithCenter(director, centerOptional.get());
      ResponseDto<DirectorVO> response = new ResponseDto<>(OK.value(), "정보 조회 성공", directorVO);
      return ResponseEntity.ok(response);
    }

    directorVO = mapper.toDirectorVO(director);
    ResponseDto<DirectorVO> response = new ResponseDto<>(OK.value(), "정보 조회 성공", directorVO);
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

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(
      OK.value(), "Center 등록 신청 완료", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/request/registerClass")
  public ResponseEntity<?> findAllRequest(@Auth String loginId) {
    log.info("loginId = {}", loginId);


    List<TeacherRegisterClassMessage> allRegisters = directorService.findAllRegisterRequest();

    ResponseDto<List<TeacherRegisterClassMessage>> responseBody = new ResponseDto<>(
      OK.value(), "Teacher Class 등록 요청 조회 성공", allRegisters);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("request/allowRegister/{messageId}")
  public ResponseEntity<?> allowRequest(@Auth String loginId, @PathVariable Long messageId) {
    log.info("loginId = {}", loginId);

    Teacher teacher = directorService.allowRegister(messageId);

    ResponseDto<Teacher> responseBody = new ResponseDto<>(
      OK.value(), "Teacher Class 등록 요청 수락 성공", teacher);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("request/rejectRegister/{messageId}")
  public ResponseEntity<?> rejectRequest(@Auth String loginId, @PathVariable Long messageId) {
    log.info("loginId = {}", loginId);

    directorService.rejectRegister(messageId);

    ResponseDto<?> responseBody = new ResponseDto<>(
      OK.value(), "Teacher Class 등록 요청 거절 성공", null);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/create/class")
  public ResponseEntity<?> createClass(@Auth String loginId, @RequestBody CreateClassRequestDto createClassRequestDto) {
    log.info("loginId = {}", loginId);
    RequestUtil.checkNeedValue(
      createClassRequestDto.getName()
    );

    Director director = directorService.findDirectorByLoginId(loginId);
    ClassVO classVO = centerService.createClass(director.getId(), createClassRequestDto.getName());

    ResponseDto<ClassVO> responseBody = new ResponseDto<>(OK.value(), "생성 성공", classVO);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/send/shuttle")
  public ResponseEntity<?> sendMessage(@Auth String loginId) {
    Director director = directorService.findDirectorByLoginId(loginId);

    if (centerService.findCenter(director.getId()).isEmpty()) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "원장의 유치원이 없습니다.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    FcmSendBody fcm = directorService.goShuttle(centerService.findCenter(director.getId()).get());

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "센터 전체 부모님 메시지 보내기 성공", fcm);
    return ResponseEntity.ok(responseBody);
  }
}

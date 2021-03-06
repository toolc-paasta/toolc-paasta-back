package toolc.daycare.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.group.request.Class.CreateClassRequestDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.director.DirectorRegisterCenterRequestDto;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.NoticeRequestDto;
import toolc.daycare.dto.member.response.director.DirectorRegisterCenterDto;
import toolc.daycare.dto.member.response.director.DirectorSignupResponseDto;
import toolc.daycare.mapper.DirectorMapper;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.ClassVO;
import toolc.daycare.vo.DirectorVO;
import toolc.daycare.vo.ParentDetailVO;

import java.io.IOException;
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
      ResponseDto<DirectorVO> response = new ResponseDto<>(OK.value(), "?????? ?????? ??????", directorVO);
      return ResponseEntity.ok(response);
    }

    directorVO = mapper.toDirectorVO(director);
    ResponseDto<DirectorVO> response = new ResponseDto<>(OK.value(), "?????? ?????? ??????", directorVO);
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

    ResponseDto<Director> responseBody = new ResponseDto<>(OK.value(), "???????????? ??????", newDirector);
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

    ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "????????? ??????", token);
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
      OK.value(), "Center ?????? ?????? ??????", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/request/registerClass")
  public ResponseEntity<?> findAllRequest(@Auth String loginId) {
    log.info("loginId = {}", loginId);


    List<TeacherRegisterClassMessage> allRegisters = directorService.findAllRegisterRequest(loginId);

    ResponseDto<List<TeacherRegisterClassMessage>> responseBody = new ResponseDto<>(
      OK.value(), "Teacher Class ?????? ?????? ?????? ??????", allRegisters);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("request/allowRegister/{messageId}")
  public ResponseEntity<?> allowRequest(@Auth String loginId, @PathVariable Long messageId) {
    log.info("loginId = {}", loginId);

    Teacher teacher = directorService.allowRegister(messageId);

    ResponseDto<Teacher> responseBody = new ResponseDto<>(
      OK.value(), "Teacher Class ?????? ?????? ?????? ??????", teacher);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("request/rejectRegister/{messageId}")
  public ResponseEntity<?> rejectRequest(@Auth String loginId, @PathVariable Long messageId) {
    log.info("loginId = {}", loginId);

    directorService.rejectRegister(messageId);

    ResponseDto<?> responseBody = new ResponseDto<>(
      OK.value(), "Teacher Class ?????? ?????? ?????? ??????", null);
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

    ResponseDto<ClassVO> responseBody = new ResponseDto<>(OK.value(), "?????? ??????", classVO);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/send/shuttle")
  public ResponseEntity<?> sendMessage(@Auth String loginId) {
    Director director = directorService.findDirectorByLoginId(loginId);

    if (centerService.findCenter(director.getId()).isEmpty()) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "????????? ???????????? ????????????.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    FcmSendBody fcm = directorService.goShuttle(centerService.findCenter(director.getId()).get());

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "?????? ?????? ????????? ????????? ????????? ??????", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/notice")
  public ResponseEntity<?> notice(@Auth String loginId, @RequestBody NoticeRequestDto dto) throws IOException {
    Director director = directorService.findDirectorByLoginId(loginId);

    Notice notice = directorService.notice(director, dto);
    directorService.sendMessage(loginId, dto.getTitle(), dto.getContent());


    ResponseDto<Notice> responseBody = new ResponseDto<>(OK.value(), "?????? ??????", notice);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/notice")
  public ResponseEntity<?> findAllNotice(@Auth String loginId) {
    Director director = directorService.findDirectorByLoginId(loginId);

    List<Notice> allNotice = directorService.findAllNotice(director);
    ResponseDto<List<Notice>> responseBody = new ResponseDto<>(OK.value(), "????????? ?????? ?????? ?????? ??????", allNotice);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/read/parents")
  public ResponseEntity<?> readParents(@Auth String loginId) {
    Director director = directorService.findDirectorByLoginId(loginId);

    Optional<Center> centerOptional = centerService.findCenter(director.getId());
    if (centerOptional.isEmpty()) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "????????? ???????????? ????????????.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    List<ParentDetailVO> parentDetailVOList = centerService
      .getAllParentsInCenter(
        centerOptional
          .get()
          .getId());

    ResponseDto<List<ParentDetailVO>> responseBody = new ResponseDto<>(
      OK.value(), "?????? ????????? ?????? ??????", parentDetailVOList);

    return ResponseEntity.ok(responseBody);
  }
}

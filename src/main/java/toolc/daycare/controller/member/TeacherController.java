package toolc.daycare.controller.member;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.RegisterClassRequestDto;
import toolc.daycare.dto.member.request.teacher.TeacherSignupRequestDto;
import toolc.daycare.dto.member.response.teacher.TeacherLoginResponseDto;
import toolc.daycare.dto.member.response.teacher.TeacherSignupResponseDto;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.TeacherService;
import toolc.daycare.util.RequestUtil;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/member/teacher")
public class TeacherController {

  private final TeacherService teacherService;

  @Autowired
  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody TeacherSignupRequestDto teacherSignupRequestDto) {
    RequestUtil.checkNeedValue(
      teacherSignupRequestDto.getLoginId(),
      teacherSignupRequestDto.getPassword(),
      teacherSignupRequestDto.getName()
    );
    RequestUtil.checkCorrectEnum(
      teacherSignupRequestDto.getSex()
    );

    Teacher newTeacher = teacherService.signUp(
      teacherSignupRequestDto.getLoginId(),
      teacherSignupRequestDto.getPassword(),
      teacherSignupRequestDto.getName(),
      teacherSignupRequestDto.getSex()
    );

    BaseResponseSuccessDto responseBody = new TeacherSignupResponseDto(newTeacher);
    return ResponseEntity.ok(responseBody);
  }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        RequestUtil.checkNeedValue(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        TokenVO token = teacherService.login(
          loginRequestDto.getLoginId(),
          loginRequestDto.getPassword(),
          loginRequestDto.getExpoToken()
        );

        ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "로그인 성공", token);
        return ResponseEntity.ok(responseBody);

//        Teacher loginTeacher = teacherService.login(
//                loginRequestDto.getLoginId(),
//                loginRequestDto.getPassword()
//        );
//
//        BaseResponseSuccessDto responseBody = new TeacherLoginResponseDto(loginTeacher);
//        return ResponseEntity.ok(responseBody);
    }

  @PostMapping("/registerClass")
  public ResponseEntity<?> registerClass(@Auth String loginId, @RequestBody RegisterClassRequestDto dto) {
    log.info(loginId);
    FcmSendBody fcm = teacherService.registerClass(loginId, dto);

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "반 등록 요청 성공", fcm);
    return ResponseEntity.ok(responseBody);
  }


  @PostMapping("/message/sendClass")
  public ResponseEntity<?> sendClass(@Auth String loginId, @RequestBody MessageSendRequestDto dto) {
    log.info(loginId);
    FcmSendBody fcm = teacherService.sendMessage(loginId, dto);

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "담당 반 부모님 메시지 보내기 성공", fcm);
    return ResponseEntity.ok(responseBody);
  }
}

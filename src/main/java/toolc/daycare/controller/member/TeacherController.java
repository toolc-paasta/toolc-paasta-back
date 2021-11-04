package toolc.daycare.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Student;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.RegisterClassRequestDto;
import toolc.daycare.dto.member.request.teacher.TeacherSignupRequestDto;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.ParentsService;
import toolc.daycare.service.member.StudentService;
import toolc.daycare.service.member.TeacherService;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.ParentVO;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/teacher")
public class TeacherController {

  private final TeacherService teacherService;
  private final StudentService studentService;
  private final ParentsService parentsService;

  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);

    ResponseDto<Teacher> response = new ResponseDto<>(OK.value(), "정보 조회 성공", teacher);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody TeacherSignupRequestDto teacherSignupRequestDto) {
    RequestUtil.checkNeedValue(
      teacherSignupRequestDto.getLoginId(),
      teacherSignupRequestDto.getPassword(),
      teacherSignupRequestDto.getName(),
      teacherSignupRequestDto.getConnectionNumber()
    );
    RequestUtil.checkCorrectEnum(
      teacherSignupRequestDto.getSex()
    );

    Teacher newTeacher = teacherService.signUp(
      teacherSignupRequestDto.getLoginId(),
      teacherSignupRequestDto.getPassword(),
      teacherSignupRequestDto.getConnectionNumber(),
      teacherSignupRequestDto.getName(),
      teacherSignupRequestDto.getSex()
    );

    ResponseDto<Teacher> responseBody = new ResponseDto<>(OK.value(), "회원가입 성공", newTeacher);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
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

  @GetMapping("/read")
  public ResponseEntity<?> findParents(@Auth String loginId) {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);

    if (teacher.getAClass() == null) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "선생님의 반이 없습니다.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    List<Student> studentList = studentService.getStudentsByClassId(teacher.getAClass().getId());
    List<Parents> parentsList = parentsService.getParentList(studentList);
    List<ParentVO> parentVOList = teacherService.findParents(parentsList);

    ResponseDto<List<ParentVO>> responseBody = new ResponseDto<>(OK.value(), "조회 성공", parentVOList);
    return ResponseEntity.ok(responseBody);
  }
}

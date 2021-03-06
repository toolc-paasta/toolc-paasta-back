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
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Student;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.NoticeRequestDto;
import toolc.daycare.dto.member.request.teacher.RegisterClassRequestDto;
import toolc.daycare.dto.member.request.teacher.TeacherSignupRequestDto;
import toolc.daycare.mapper.TeacherMapper;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.service.member.ParentsService;
import toolc.daycare.service.member.StudentService;
import toolc.daycare.service.member.TeacherService;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.ParentVO;
import toolc.daycare.vo.TeacherVO;

import java.io.IOException;
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
  private final CenterService centerService;
  private final DirectorService directorService;
  private final TeacherMapper mapper = new TeacherMapper();

  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);
    TeacherVO teacherVO;

    if (teacher.getAClass() == null) {
      teacherVO = mapper.toTeacherVOExcludeClass(teacher);
      ResponseDto<TeacherVO> response = new ResponseDto<>(OK.value(), "?????? ?????? ??????", teacherVO);
      return ResponseEntity.ok(response);
    }

    if (teacher.getAClass().getCenter() == null) {
      teacherVO = mapper.toTeacherVOExcludeDirector(teacher);
      ResponseDto<TeacherVO> response = new ResponseDto<>(OK.value(), "?????? ?????? ??????", teacherVO);
      return ResponseEntity.ok(response);
    }

    teacherVO = mapper.toTeacherVO(teacher);
    ResponseDto<TeacherVO> response = new ResponseDto<>(OK.value(), "?????? ?????? ??????", teacherVO);
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
      teacherSignupRequestDto.getName(),
      teacherSignupRequestDto.getConnectionNumber(),
      teacherSignupRequestDto.getSex()
    );

    ResponseDto<Teacher> responseBody = new ResponseDto<>(OK.value(), "???????????? ??????", newTeacher);
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

    ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "????????? ??????", token);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/registerClass")
  public ResponseEntity<?> registerClass(@Auth String loginId, @RequestBody RegisterClassRequestDto dto) {
    log.info(loginId);
    FcmSendBody fcm = teacherService.registerClass(loginId, dto);

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "??? ?????? ?????? ??????", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/message/sendClass")
  public ResponseEntity<?> sendClass(@Auth String loginId, @RequestBody MessageSendRequestDto dto) {
    log.info(loginId);
    FcmSendBody fcm = teacherService.sendMessage(loginId, dto);

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "?????? ??? ????????? ????????? ????????? ??????", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/read")
  public ResponseEntity<?> findParents(@Auth String loginId) {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);

    if (teacher.getAClass() == null) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "???????????? ?????? ????????????.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    List<Student> studentList = studentService.getStudentsByClassId(teacher.getAClass().getId());
    List<Parents> parentsList = parentsService.getParentList(studentList);
    List<ParentVO> parentVOList = teacherService.findParents(parentsList);

    ResponseDto<List<ParentVO>> responseBody = new ResponseDto<>(OK.value(), "?????? ??????", parentVOList);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/notice")
  public ResponseEntity<?> notice(@Auth String loginId, @RequestBody NoticeRequestDto dto) throws IOException {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);

    Notice notice = teacherService.notice(teacher, dto);

    Class teacherClass = teacher.getAClass();
    teacherService.sendMessage(loginId,
      new MessageSendRequestDto(teacherClass.getCenter().getName(), teacherClass.getName(),
        teacherClass.getName() + "????????? ????????????.", dto.getTitle()));

    ResponseDto<Notice> responseBody = new ResponseDto<>(OK.value(), "?????? ??????", notice);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/enter/child/{id}")
  public ResponseEntity<?> enterChild(@Auth String loginId, @PathVariable("id") Long studentId) {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);
    Class aClass = teacher.getAClass();

    if (aClass == null) {
      ResponseDto<?> responseBody = new ResponseDto<>(BAD_REQUEST.value(), "???????????? ?????? ????????????.", null);
      return ResponseEntity.badRequest().body(responseBody);
    }

    studentService.enterClass(aClass, studentId);

    ResponseDto<Notice> responseBody = new ResponseDto<>(OK.value(), "????????? ?????? ?????? ??????", null);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/send/shuttle")
  public ResponseEntity<?> sendMessage(@Auth String loginId) {
    Center center = teacherService.findTeacherByLoginId(loginId).getAClass().getCenter();

    FcmSendBody fcm = directorService.goShuttle(center);

    ResponseDto<FcmSendBody> responseBody = new ResponseDto<>(OK.value(), "?????? ?????? ????????? ????????? ????????? ??????", fcm);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/notice")
  public ResponseEntity<?> findAllNotice(@Auth String loginId) {
    Teacher teacher = teacherService.findTeacherByLoginId(loginId);

    List<Notice> allNotice = teacherService.findAllNotice(teacher);

    ResponseDto<List<Notice>> responseBody = new ResponseDto<>(OK.value(), "????????? ?????? ?????? ?????? ??????",
      allNotice);
    return ResponseEntity.ok(responseBody);
  }
}

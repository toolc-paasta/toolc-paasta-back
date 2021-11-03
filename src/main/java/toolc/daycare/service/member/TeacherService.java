package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Student;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.RegisterClassRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;
import toolc.daycare.repository.interfaces.member.StudentRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
import toolc.daycare.repository.interfaces.message.TeacherRegisterClassRepository;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.fcm.FcmSender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TeacherService {

  private final MemberService memberService;
  private final TokenService tokenService;
  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;
  private final ParentsRepository parentsRepository;
  private final ClassRepository classRepository;
  private final CenterRepository centerRepository;
  private final FcmSender fcmSender;
  private final PasswordEncoder passwordEncoder;
  private final TeacherRegisterClassRepository registerClassRepository;


  @Autowired
  public TeacherService(MemberService memberService,
                        TokenService tokenService,
                        TeacherRepository teacherRepository,
                        StudentRepository studentRepository,
                        ParentsRepository parentsRepository,
                        ClassRepository classRepository,
                        CenterRepository centerRepository,
                        FcmSender fcmSender,
                        PasswordEncoder passwordEncoder,
                        TeacherRegisterClassRepository registerClassRepository) {
    this.memberService = memberService;
    this.tokenService = tokenService;
    this.teacherRepository = teacherRepository;
    this.studentRepository = studentRepository;
    this.parentsRepository = parentsRepository;
    this.classRepository = classRepository;
    this.centerRepository = centerRepository;
    this.fcmSender = fcmSender;
    this.passwordEncoder = passwordEncoder;
    this.registerClassRepository = registerClassRepository;
  }

  public Teacher signUp(String loginId, String password, String name,
                        String connectionNumber, Sex sex) {
    memberService.checkDuplicateMember(loginId);
    Teacher teacher = Teacher.builder()
      .loginId(loginId)
      .password(passwordEncoder.encode(password))
      .name(name)
      .connectionNumber(connectionNumber)
      .sex(sex)
      .build();

    return teacherRepository.save(teacher);
  }

  public TokenVO login(String loginId, String password, String expoToken) {
    Teacher teacher = teacherRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
    memberService.checkLoginPassword(teacher, password);

    teacher.setExpoToken(expoToken);
    teacherRepository.save(teacher);

    AccessToken accessToken = tokenService.create(loginId);
    return tokenService.formatting(accessToken);
  }

  public FcmSendBody registerClass(String loginId, RegisterClassRequestDto dto) {
    Teacher teacher = teacherRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);

    Long centerId = centerRepository.findByName(dto.getCenterName()).getId();
    Class registerClass = classRepository.findByNameAndCenterId(dto.getClassName(), centerId);

    log.info("class name = {}", registerClass.getName());

    String title = teacher.getName() + "선생님의 " + registerClass.getName() + " 반 등록 신청입니다.";
    String body = teacher.getName() + "선생님의 " + registerClass.getName() + " 반 등록 신청입니다.";

    Map<String, Object> data = new HashMap<>();
    data.put("temp", "temp");

    List<String> targetUser = new LinkedList<>();
    //Todo :: 예외 처리 못해줌 - 정상 입력 아닐 경우
    targetUser.add(registerClass.getCenter().getDirector().getLoginId());

    FcmSendBody fcmSendBody = fcmSender.sendFcmJson(title, body, targetUser, data);

    TeacherRegisterClassMessage registerClassMessage = new TeacherRegisterClassMessage(
      teacher, centerId, registerClass.getId());
    registerClassRepository.save(registerClassMessage);

    return fcmSendBody;

  }


  public FcmSendBody sendMessage(String loginId, MessageSendRequestDto dto) {
    Teacher teacher = teacherRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);

    List<String> targetUser = new LinkedList<>();

    Class targetClass = teacher.getAClass();
    List<Student> students = studentRepository.findByaClassId(targetClass.getId());
    students.forEach(student -> parentsRepository.findByStudentId(student.getId())
      .forEach(parent -> targetUser.add(parent.getLoginId())));

    Map<String, Object> data = new HashMap<>();
    data.put("temp", "temp");

    return fcmSender.sendFcmJson(dto.getTitle(), dto.getBody(), targetUser, data);

  }
}

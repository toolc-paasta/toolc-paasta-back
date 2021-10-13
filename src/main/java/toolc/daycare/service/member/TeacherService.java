package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Student;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;
import toolc.daycare.repository.interfaces.member.StudentRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
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
  private final FcmSender fcmSender;
  private final PasswordEncoder passwordEncoder;


  @Autowired
  public TeacherService(MemberService memberService,
                        TokenService tokenService,
                        TeacherRepository teacherRepository,
                        StudentRepository studentRepository,
                        ParentsRepository parentsRepository,
                        FcmSender fcmSender,
                        PasswordEncoder passwordEncoder) {
    this.memberService = memberService;
    this.tokenService = tokenService;
    this.teacherRepository = teacherRepository;
    this.studentRepository = studentRepository;
    this.parentsRepository = parentsRepository;
    this.fcmSender = fcmSender;
    this.passwordEncoder = passwordEncoder;
  }

  public Teacher signUp(String loginId, String password, String name, Sex sex) {
    memberService.checkDuplicateMember(loginId);
    Teacher teacher = Teacher.builder()
      .loginId(loginId)
      .password(passwordEncoder.encode(password))
      .name(name)
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

    return fcmSender.sendFcmJson(dto.getTitle(), dto.getBody(),targetUser, data);

  }
}

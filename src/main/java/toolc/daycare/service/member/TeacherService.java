package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
import toolc.daycare.service.fcm.FcmSendBody;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class TeacherService {

  private final MemberService memberService;
  private final TeacherRepository teacherRepository;
  private final CenterRepository centerRepository;
  private final ClassRepository classRepository;
  private final PasswordEncoder passwordEncoder;



  @Autowired
  public TeacherService(MemberService memberService,
                        TeacherRepository teacherRepository,
                        CenterRepository centerRepository,
                        ClassRepository classRepository,
                        PasswordEncoder passwordEncoder) {
    this.memberService = memberService;
    this.teacherRepository = teacherRepository;
    this.centerRepository = centerRepository;
    this.classRepository = classRepository;
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

  public Teacher login(String loginId, String password, String expoToken) {
    Teacher teacher = teacherRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);

    memberService.checkLoginPassword(teacher, password);
    teacher.setExpoToken(expoToken);
    return teacher;
  }


//  public FcmSendBody sendMessage(String loginId, MessageSendRequestDto dto) {
//    Teacher teacher = teacherRepository.findByLoginId(loginId)
//      .orElseThrow(NotExistMemberException::new);
//    List<String> targetUser = new LinkedList<>();
//    Long centerId = centerRepository.findByName(dto.getCenterName()).getId();
//
//
//
//  }
}

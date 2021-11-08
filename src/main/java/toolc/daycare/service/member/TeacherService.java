package toolc.daycare.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.config.s3.S3Uploader;
import toolc.daycare.domain.group.Class;
//import toolc.daycare.domain.group.Notice;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.domain.member.*;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.dto.member.request.teacher.NoticeRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.RegisterClassRequestDto;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
//import toolc.daycare.repository.interfaces.group.NoticeRepository;
import toolc.daycare.repository.interfaces.group.NoticeRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;
import toolc.daycare.repository.interfaces.member.StudentRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
import toolc.daycare.repository.interfaces.message.TeacherRegisterClassRepository;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.fcm.FcmSender;
import toolc.daycare.vo.ParentVO;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static java.util.Base64.getDecoder;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
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
  private final S3Uploader s3Uploader;
  private final NoticeRepository noticeRepository;

  public Teacher findTeacherByLoginId(String loginId) {
    return teacherRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
  }

  public Teacher signUp(String loginId, String password, String name, String connectionNumber, Sex sex) {
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

    AccessToken accessToken = tokenService.create(loginId, Authority.TEACHER);
    return tokenService.formatting(accessToken);
  }

  public FcmSendBody registerClass(String loginId, RegisterClassRequestDto dto) {
    Teacher teacher = teacherRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);

    Long centerId = centerRepository.findByName(dto.getCenterName())
      .getId();
    Class registerClass = classRepository.findByNameAndCenterId(dto.getClassName(), centerId);

    log.info("class name = {}", registerClass.getName());

    String title = teacher.getName() + "선생님의 " + registerClass.getName() + " 반 등록 신청입니다.";
    String body = teacher.getName() + "선생님의 " + registerClass.getName() + " 반 등록 신청입니다.";

    Map<String, Object> data = new HashMap<>();
    data.put("temp", "temp");

    List<String> targetUser = new LinkedList<>();
    //Todo :: 예외 처리 못해줌 - 정상 입력 아닐 경우
    targetUser.add(registerClass.getCenter()
      .getDirector()
      .getLoginId());

    FcmSendBody fcmSendBody = fcmSender.sendFcmJson(title, body, targetUser, data);

    TeacherRegisterClassMessage registerClassMessage = new TeacherRegisterClassMessage(teacher, centerId, registerClass.getId());
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

  public List<ParentVO> findParents(List<Parents> parentsList) {
    List<ParentVO> parents = new ArrayList<>();
    parentsList.forEach(p -> {
      parents.add(ParentVO.builder()
        .name(p.getName())
        .sex(p.getSex())
        .connectionNumber(p.getConnectionNumber())
        .loginId(p.getLoginId())
        .childId(p.getStudent().getId())
        .childName(p.getChildName())
        .childSex(p.getStudent()
          .getSex())
        .childBirthday(p.getChildBirthday())
        .build());
    });
    return parents;
  }

  public Notice notice(Teacher teacher, NoticeRequestDto dto) throws IOException {
    String imgUrl = s3Uploader.upload(getDecoder().decode(dto.getImg()));
    Notice notice = new Notice(dto.getTitle(), dto.getContent(), LocalDate.now(),
      teacher.getName(), imgUrl, teacher.getAClass().getCenter());
    return noticeRepository.save(notice);
  }
}
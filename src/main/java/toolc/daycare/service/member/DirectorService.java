package toolc.daycare.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.config.s3.S3Uploader;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.dto.member.request.teacher.MessageSendRequestDto;
import toolc.daycare.dto.member.request.teacher.NoticeRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.fcm.FcmWebClient;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.repository.interfaces.group.NoticeRepository;
import toolc.daycare.repository.interfaces.member.*;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;
import toolc.daycare.repository.interfaces.message.TeacherRegisterClassRepository;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.fcm.FcmSender;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;

import javax.transaction.Transactional;
import java.io.IOException;

import java.time.LocalDate;
import java.util.*;

import static java.util.Base64.getDecoder;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DirectorService {

  private final MemberService memberService;
  private final DirectorRepository directorRepository;
  private final AdminRepository adminRepository;
  private final ClassRepository classRepository;
  private final TeacherRepository teacherRepository;
  private final PasswordEncoder passwordEncoder;
  private final FcmSender fcmSender;
  private final TokenService tokenService;
  private final CenterRegisterRepository centerRegisterRepository;
  private final TeacherRegisterClassRepository teacherRegisterClassRepository;
  private final StudentRepository studentRepository;
  private final ParentsRepository parentsRepository;
  private final NoticeRepository noticeRepository;
  private final S3Uploader s3Uploader;
  private final CenterRepository centerRepository;


  public Director signUp(String loginId, String password, String name, String connectionNumber,
                         Sex sex) {
    memberService.checkDuplicateMember(loginId);
    Director director = Director.builder()
      .loginId(loginId)
      .password(passwordEncoder.encode(password))
      .name(name)
      .connectionNumber(connectionNumber)
      .sex(sex)
      .build();

    return directorRepository.save(director);
  }


  public TokenVO login(String loginId, String password, String expoToken) {
    Director director = directorRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
    memberService.checkLoginPassword(director, password);

    director.setExpoToken(expoToken);

    AccessToken accessToken = tokenService.create(loginId, director.getAuthority());

    return tokenService.formatting(accessToken);
  }

  public FcmSendBody centerRegister(String loginId, String centerName, String address,
                                    LocalDate foundationDate) {
    // TODO : ????????? ????????? ????????? ???????????? ?????????? - DIRECTOR ???????????? ??? ??????????
    Director director = directorRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
    String title = "Center ?????? ??????";
    String body = director.getName() + " ?????? " + centerName + " Center ?????? ??????";

    List<String> targetUser = new LinkedList<>();
    adminRepository.findAll()
      .forEach(e -> targetUser.add(e.getLoginId()));

    Map<String, Object> data = new HashMap<>();
    data.put("centerName", centerName);
    data.put("address", address);
    data.put("foundationDate", foundationDate);

    CenterRegisterMessage message = new CenterRegisterMessage(director, centerName, address,
      foundationDate);
    centerRegisterRepository.save(message);

    // TODO : ????????? ????????? ????????? ???????????? ??????????
    return fcmSender.sendFcmJson(/*director.getName(),*/ title, body, targetUser, data);
  }


  public List<TeacherRegisterClassMessage> findAllRegisterRequest(String loginId) {
    Center center = centerRepository.findByDirectorId(
        directorRepository.findByLoginId(loginId)
          .get()
          .getId())
      .get();
    return teacherRegisterClassRepository.findByCenterId(center.getId());
  }

  public Teacher allowRegister(Long messageId) {
    TeacherRegisterClassMessage message = teacherRegisterClassRepository.findById(messageId)
      .get();
    Teacher teacher = message.getTeacher();
    teacher.setAClass(classRepository.findById(message.getClassId())
      .get());

    teacherRepository.save(teacher);
    teacherRegisterClassRepository.deleteById(messageId);

    List<String> targetUser = new LinkedList<>();
    Map<String, Object> data = new HashMap<>();

    targetUser.add(message.getTeacher()
      .getLoginId());
    data.put("temp", "temp");

    fcmSender.sendFcmJson("??? ?????? ?????? ?????????????????????.", message.getTeacher()
        .getName() + "?????? ?????? ???????????????."
      , targetUser, data);

    return teacher;
  }

  public void rejectRegister(Long messageId) {
    List<String> targetUser = new LinkedList<>();
    Map<String, Object> data = new HashMap<>();

    TeacherRegisterClassMessage reject = teacherRegisterClassRepository.findById(messageId)
      .get();

    targetUser.add(reject.getTeacher()
      .getLoginId());
    data.put("temp", "temp");
    fcmSender.sendFcmJson("??? ?????? ?????? ?????????????????????.", reject.getTeacher()
        .getName() + "?????? ?????? ???????????????."
      , targetUser, data);
    teacherRegisterClassRepository.deleteById(messageId);
  }


  public Director findDirectorByLoginId(String loginId) {
    return directorRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
  }

  public Notice notice(Director director, NoticeRequestDto dto) throws IOException {
    Center center =
      centerRepository.findByDirectorId(director.getId())
        .orElseThrow(IllegalArgumentException::new);
    String imgUrl = "";
    if (dto.getImg() != null) {
      imgUrl = s3Uploader.upload(getDecoder().decode(dto.getImg()));
    }
    Notice notice = new Notice(dto.getTitle(), dto.getContent(), LocalDate.now(),
      director.getName(), imgUrl, center);
    return noticeRepository.save(notice);
  }

  public List<Notice> findAllNotice(Director director) {
    Center center = centerRepository.findByDirectorId(director.getId())
      .orElseThrow(IllegalArgumentException::new);
    return noticeRepository.findByCenterId(center.getId());
  }

  public FcmSendBody sendMessage(String loginId, String title, String content) {
    Director director = directorRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
    List<String> targetUser = new LinkedList<>();

    Center center =
      centerRepository.findByDirectorId(director.getId())
        .orElseThrow(IllegalArgumentException::new);
    List<Class> classList = classRepository.findByCenterId(center.getId());

    classList.forEach(aClass -> studentRepository.findByaClassId(aClass.getId())
      .forEach(student -> parentsRepository.findByStudentId(student.getId())
        .forEach(parents -> targetUser.add(parents.getLoginId()))));

    Map<String, Object> data = new HashMap<>();
    data.put("temp", "temp");

    return fcmSender.sendFcmJson(title, content, targetUser, data);
  }

  public FcmSendBody goShuttle(Center center) {
    String title = center.getName() + "??? ?????? ????????? ??????????????????!";
    String body = "????????? ????????? ???????????? ????????? ???" + center.getName() + "??? ????????? ?????????????????? ^.^";

    List<String> targetUser = new LinkedList<>();
    classRepository.findByCenterId(center.getId())
      .forEach(aClass -> studentRepository.findByaClassId(aClass.getId())
        .forEach(student -> parentsRepository.findByStudentId(student.getId())
          .forEach(parents -> targetUser.add(parents.getLoginId()))));

    log.info("target Number is {}", targetUser.size());

    return fcmSender.sendFcmJson(title, body, targetUser, Map.of());
  }
}

package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.config.s3.S3Uploader;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.dto.member.request.teacher.NoticeRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.fcm.FcmWebClient;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.repository.interfaces.group.NoticeRepository;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
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
  private final NoticeRepository noticeRepository;
  private final S3Uploader s3Uploader;
  private final CenterRepository centerRepository;

  @Autowired
  public DirectorService(MemberService memberService,
                         DirectorRepository directorRepository,
                         AdminRepository adminRepository,
                         PasswordEncoder passwordEncoder,
                         FcmWebClient fcmWebClient,
                         ClassRepository classRepository,
                         TeacherRepository teacherRepository,
                         FcmSender fcmSender,
                         TokenService tokenService,
                         CenterRegisterRepository centerRegisterRepository,
                         TeacherRegisterClassRepository teacherRegisterClassRepository,
                         NoticeRepository noticeRepository,
                         S3Uploader s3Uploader,
                         CenterRepository centerRepository) {
    this.memberService = memberService;
    this.adminRepository = adminRepository;
    this.directorRepository = directorRepository;
    this.passwordEncoder = passwordEncoder;
    this.classRepository = classRepository;
    this.teacherRepository = teacherRepository;
    this.fcmSender = fcmSender;
    this.tokenService = tokenService;
    this.centerRegisterRepository = centerRegisterRepository;
    this.teacherRegisterClassRepository = teacherRegisterClassRepository;
    this.noticeRepository = noticeRepository;
    this.s3Uploader = s3Uploader;
    this.centerRepository = centerRepository;
  }

  public Director signUp(String loginId, String password, String name, String connectionNumber, Sex sex) {
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

  public FcmSendBody centerRegister(String loginId, String centerName, String address, LocalDate foundationDate) {

    // TODO : 메세지 보내는 사람도 필요하지 않을까? - DIRECTOR 불러줘야 할 것인가?
    Director director = directorRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
    String title = "Center 등록 신청";
    String body = director.getName() + " 님의 " + centerName + " Center 등록 신청";

    List<String> targetUser = new LinkedList<>();
    adminRepository.findAll().forEach(e -> targetUser.add(e.getLoginId()));

    Map<String, Object> data = new HashMap<>();
    data.put("centerName", centerName);
    data.put("address", address);
    data.put("foundationDate", foundationDate);

    CenterRegisterMessage message = new CenterRegisterMessage(director, centerName, address,
      foundationDate);
    centerRegisterRepository.save(message);

    // TODO : 메세지 보내는 사람도 필요하지 않을까?
    return fcmSender.sendFcmJson(/*director.getName(),*/ title, body, targetUser, data);

  }

  
  public List<TeacherRegisterClassMessage> findAllRegisterRequest() {

    return teacherRegisterClassRepository.findAll();
  }

  public Teacher allowRegister(Long messageId) {
    TeacherRegisterClassMessage message = teacherRegisterClassRepository.findById(messageId).get();
    Teacher teacher = message.getTeacher();
    teacher.setAClass(classRepository.findById(message.getClassId()).get());

    teacherRepository.save(teacher);
    teacherRegisterClassRepository.deleteById(messageId);
    return teacher;
  }

  public void rejectRegister(Long messageId) {
    teacherRegisterClassRepository.deleteById(messageId);
  }


  public Director findDirectorByLoginId(String loginId) {
    return directorRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
  }

  public Notice notice(Director director, NoticeRequestDto dto) throws IOException {
    Center center =
      centerRepository.findByDirectorId(director.getId()).orElseThrow(IllegalArgumentException::new);
    String imgUrl = s3Uploader.upload(getDecoder().decode(dto.getImg()));
    Notice notice = new Notice(dto.getTitle(), dto.getContent(), LocalDate.now(),
                               director.getName(), imgUrl, center);
    return noticeRepository.save(notice);
  }
}

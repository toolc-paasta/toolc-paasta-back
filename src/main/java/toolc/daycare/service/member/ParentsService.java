package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Student;
import toolc.daycare.dto.member.request.parents.ParentsSignupRequestDto;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.group.NoticeRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;
import toolc.daycare.repository.interfaces.member.StudentRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
import toolc.daycare.vo.ParentVO;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ParentsService {

  private final MemberService memberService;
  private final ParentsRepository parentsRepository;
  private final TeacherRepository teacherRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final NoticeRepository noticeRepository;
  private final StudentRepository studentRepository;

  @Autowired
  public ParentsService(MemberService memberService,
                        ParentsRepository parentsRepository,
                        TeacherRepository teacherRepository,
                        PasswordEncoder passwordEncoder,
                        TokenService tokenService,
                        NoticeRepository noticeRepository, StudentRepository studentRepository) {
    this.memberService = memberService;
    this.parentsRepository = parentsRepository;
    this.teacherRepository = teacherRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
    this.noticeRepository = noticeRepository;
    this.studentRepository = studentRepository;
  }

  public Parents findParentsByLoginId(String loginId) {
    return parentsRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
  }

  public Parents signUp(String loginId, String password, String name, Sex sex,
                        String connectionNumber, String childName, LocalDate childBirthday,
                        Sex childSex) {
    memberService.checkDuplicateMember(loginId);
    Parents parents = Parents.builder()
      .loginId(loginId)
      .password(passwordEncoder.encode(password))
      .name(name)
      .connectionNumber(connectionNumber)
      .sex(sex)
      .childName(childName)
      .childBirthday(childBirthday)
      .childSex(childSex)
      .build();

    Student student = Student.builder()
      .name(childName)
      .sex(childSex)
      .birthday(childBirthday)
      .build();

    studentRepository.save(student);
    parents.setStudents(student);
    return parentsRepository.save(parents);
  }

  public TokenVO login(String loginId, String password, String expoToken) {
    Parents parents = parentsRepository.findByLoginId(loginId)
      .orElseThrow(NotExistMemberException::new);
    memberService.checkLoginPassword(parents, password);

    parents.setExpoToken(expoToken);

    AccessToken accessToken = tokenService.create(loginId, parents.getAuthority());

    return tokenService.formatting(accessToken);
  }

  public ParentVO search(String name, String connectionNumber) {
    Parents parents = parentsRepository.findByNameAndConnectionNumber(name, connectionNumber);
    if (parents == null) {
      return null;
    }
    return ParentVO.builder()
      .name(parents.getName())
      .connectionNumber(parents.getConnectionNumber())
      .childSex(parents.getChildSex())
      .childName(parents.getChildName())
      .childId(parents.getStudent()
        .getId())
      .loginId(parents.getLoginId())
      .sex(parents.getSex())
      .childBirthday(parents.getChildBirthday())
      .build();
  }

  public List<Parents> getParentList(List<Student> studentList) {
    List<Parents> parentsList = new ArrayList<>();
    studentList.forEach(s -> parentsList.addAll(parentsRepository.findByStudentId(s.getId())));
    return parentsList;
  }

  public List<Notice> getAllNotice(String loginId) {
    Parents parents = parentsRepository.findByLoginId(loginId)
      .get();
    log.info("parents = {}", parents.getName());
    log.info("student = {}", parents.getStudent());
    log.info("class ={}", parents.getStudent().getAClass());
    log.info("center = {}", parents.getStudent().getAClass().getCenter());
    log.info("centerId = {}", parents.getStudent().getAClass().getCenter().getId());
    return noticeRepository.findByCenterId(parentsRepository.findByLoginId(loginId).get()
      .getStudent().getAClass().getCenter().getId());
  }

  public Parents signUpAsSpouse(ParentsSignupRequestDto dto, Student student) {
    memberService.checkDuplicateMember(dto.getLoginId());
    Parents parents = Parents.builder()
      .loginId(dto.getLoginId())
      .password(passwordEncoder.encode(dto.getPassword()))
      .name(dto.getName())
      .connectionNumber(dto.getConnectionNumber())
      .sex(dto.getSex())
      .childName(student.getName())
      .childBirthday(student.getBirthday())
      .childSex(student.getSex())
      .build();

    parents.setStudents(student);
    return parentsRepository.save(parents);
  }
}

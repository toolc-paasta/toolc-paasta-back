package toolc.daycare.service.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Student;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;
import toolc.daycare.repository.interfaces.member.StudentRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;
import toolc.daycare.service.fcm.FcmSender;
import toolc.daycare.vo.ParentVO;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static toolc.daycare.Fixture.parent;
import static toolc.daycare.Fixture.student;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
  @Mock
  MemberService memberService;
  @Mock
  TokenService tokenService;
  @Mock
  TeacherRepository teacherRepository;
  @Mock
  StudentRepository studentRepository;
  @Mock
  ParentsRepository parentsRepository;
  @Mock
  ClassRepository classRepository;
  @Mock
  CenterRepository centerRepository;
  @Mock
  FcmSender fcmSender;
  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  TeacherService teacherService;

  @Test
  void 맡은_반_학부모리스트() {
    // given
    List<Parents> parentsList = new ArrayList<>();
    List<ParentVO> target = new ArrayList<>();
    Parents parents1 = parent().build();
    Parents parents2 = parent()
      .loginId("parent2001")
      .name("parent2")
      .sex(Sex.WOMAN)
      .connectionNumber("010-2222-2222")
      .build();
    Student student = student().build();

    parents1.setStudents(student);
    parents2.setStudents(student);
    parentsList.add(parents1);
    parentsList.add(parents2);

    target.add(ParentVO.builder()
      .loginId(parents1.getLoginId())
      .childId(parents1.getStudent().getId())
      .sex(parents1.getSex())
      .childName(parents1.getChildName())
      .childSex(parents1.getChildSex())
      .connectionNumber(parents1.getConnectionNumber())
      .name(parents1.getName())
      .childBirthday(parents1.getChildBirthday())
      .build());
    target.add(ParentVO.builder()
      .loginId(parents2.getLoginId())
      .childId(parents2.getStudent().getId())
      .sex(parents2.getSex())
      .childName(parents2.getChildName())
      .childSex(parents2.getChildSex())
      .connectionNumber(parents2.getConnectionNumber())
      .name(parents2.getName())
      .childBirthday(parents2.getChildBirthday())
      .build());

    // when
    List<ParentVO> parents = teacherService.findParents(parentsList);

    // then
    assertThat(parents, is(target));
  }
}
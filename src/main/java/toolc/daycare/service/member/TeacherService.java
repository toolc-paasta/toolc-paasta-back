package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.exception.DuplicateMemberException;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;

@Slf4j
@Service
public class TeacherService {

    private final MemberService memberService;
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(MemberService memberService,
                          TeacherRepository teacherRepository) {
        this.memberService = memberService;
        this.teacherRepository = teacherRepository;
    }

    public Teacher signUp(String loginId, String password, String name, Sex sex) {
        memberService.checkDuplicateMember(loginId);
        Teacher teacher = Teacher.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .sex(sex)
                .build();

        return teacherRepository.save(teacher);
    }




}

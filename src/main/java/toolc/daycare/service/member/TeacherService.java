package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.exception.DuplicateMemberException;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;

@Slf4j
@Service
public class TeacherService {

    private final MemberService memberService;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public TeacherService(MemberService memberService,
                          TeacherRepository teacherRepository,
                          PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.teacherRepository = teacherRepository;
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

    public Teacher login(String loginId, String password){
        Teacher teacher = teacherRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        memberService.checkLoginPassword(teacher, password);
        return teacher;
    }




}

package toolc.daycare.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.TeacherRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeacherService {
    private final MemberService memberService;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

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

    public TokenVO login(String loginId, String password){
        Teacher teacher = teacherRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        memberService.checkLoginPassword(teacher, password);

        AccessToken accessToken = tokenService.create(loginId, teacher.getAuthority());

        return tokenService.formatting(accessToken);
    }
}

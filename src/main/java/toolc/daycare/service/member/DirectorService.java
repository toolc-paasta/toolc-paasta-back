package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.MemberRepository;

@Slf4j
@Service
public class DirectorService {

    private final MemberService memberService;
    private final DirectorRepository directorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DirectorService(MemberService memberService,
                           DirectorRepository directorRepository,
                           PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.directorRepository = directorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Director signUp(String loginId, String password, String name, Sex sex) {
        memberService.checkDuplicateMember(loginId);
        Director director = Director.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .sex(sex)
                .build();

        return directorRepository.save(director);
    }

    public Director login(String loginId, String password){
        Director director = directorRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        memberService.checkLoginPassword(director, password);
        return director;
    }

}

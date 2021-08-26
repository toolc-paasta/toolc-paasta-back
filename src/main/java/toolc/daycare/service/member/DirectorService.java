package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.MemberRepository;

@Slf4j
@Service
public class DirectorService {

    private final MemberService memberService;
    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(MemberService memberService,
                           DirectorRepository directorRepository) {
        this.memberService = memberService;
        this.directorRepository = directorRepository;
    }

    public Director signUp(String loginId, String password, String name, Sex sex) {
        memberService.checkDuplicateMember(loginId);
        Director director = Director.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .sex(sex)
                .build();

        return directorRepository.save(director);
    }

}

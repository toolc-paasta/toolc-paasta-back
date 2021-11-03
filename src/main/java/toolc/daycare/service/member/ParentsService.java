package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
@Transactional
public class ParentsService {

    private final MemberService memberService;
    private final ParentsRepository parentsRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Autowired
    public ParentsService(MemberService memberService,
                          ParentsRepository parentsRepository,
                          PasswordEncoder passwordEncoder,
                          TokenService tokenService) {
        this.memberService = memberService;
        this.parentsRepository = parentsRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public Parents findParentsByLoginId(String loginId) {
        return parentsRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
    }

    public Parents signUp(String loginId, String password, String name, Sex sex,
                          String childName, LocalDate childBirthday, Sex childSex) {
        memberService.checkDuplicateMember(loginId);
        Parents parents = Parents.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .sex(sex)
                .childName(childName)
                .childBirthday(childBirthday)
                .childSex(childSex)
                .build();

        return parentsRepository.save(parents);
    }

    public TokenVO login(String loginId, String password, String expoToken){
        Parents parents = parentsRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        memberService.checkLoginPassword(parents, password);

        parents.setExpoToken(expoToken);

        AccessToken accessToken = tokenService.create(loginId, parents.getAuthority());

        return tokenService.formatting(accessToken);
    }
}

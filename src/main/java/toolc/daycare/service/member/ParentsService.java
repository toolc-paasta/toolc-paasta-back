package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
public class ParentsService {

    private final MemberService memberService;
    private final ParentsRepository parentsRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ParentsService(MemberService memberService,
                          ParentsRepository parentsRepository,
                          PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.parentsRepository = parentsRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Parents login(String loginId, String password){
        Parents parents = parentsRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        memberService.checkLoginPassword(parents, password);
        return parents;
    }
}

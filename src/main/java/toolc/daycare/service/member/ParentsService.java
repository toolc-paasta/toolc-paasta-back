package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.ParentsRepository;

import java.util.Date;

@Slf4j
@Service
public class ParentsService {

    private final MemberService memberService;
    private final ParentsRepository parentsRepository;

    @Autowired
    public ParentsService(MemberService memberService,
                          ParentsRepository parentsRepository) {
        this.memberService = memberService;
        this.parentsRepository = parentsRepository;
    }

    public Parents signUp(String loginId, String password, String name, Sex sex,
                          String childName, Date childBirthday, Sex childSex) {
        memberService.checkDuplicateMember(loginId);
        Parents parents = Parents.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .sex(sex)
                .childName(childName)
                .childBirthday(childBirthday)
                .childSex(childSex)
                .build();

        return parentsRepository.save(parents);
    }

}

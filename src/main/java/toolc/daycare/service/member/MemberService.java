package toolc.daycare.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.exception.DuplicateMemberException;
import toolc.daycare.repository.interfaces.member.MemberRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    protected void checkDuplicateMember(String loginId) {
        memberRepository.findByLoginId(loginId).ifPresent(m -> {
            throw new DuplicateMemberException();
        });
    }
}

package toolc.daycare.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.MemberBaseEntity;
import toolc.daycare.exception.DuplicateMemberException;
import toolc.daycare.exception.InvalidPasswordException;
import toolc.daycare.repository.interfaces.member.MemberRepository;
import toolc.daycare.repository.interfaces.member.TeacherRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    protected void checkDuplicateMember(String loginId) {
        memberRepository.findByLoginId(loginId).ifPresent(m -> {
            throw new DuplicateMemberException();
        });
    }

    protected void checkLoginPassword(MemberBaseEntity member, String password){
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new InvalidPasswordException();
        }
    }
}

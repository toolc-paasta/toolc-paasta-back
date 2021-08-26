package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.MemberBaseEntity;

import java.util.Optional;

public interface MemberRepository {
    MemberBaseEntity save(MemberBaseEntity member);
    Optional <MemberBaseEntity> findByLoginId(String loginId);

}

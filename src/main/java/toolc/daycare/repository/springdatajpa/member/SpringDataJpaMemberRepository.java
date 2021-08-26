package toolc.daycare.repository.springdatajpa.member;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.MemberBaseEntity;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.member.MemberRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<MemberBaseEntity, Long>, MemberRepository {
}

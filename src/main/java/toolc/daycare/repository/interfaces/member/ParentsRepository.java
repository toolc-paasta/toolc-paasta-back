package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Teacher;

import java.util.List;
import java.util.Optional;

public interface ParentsRepository {
  Parents save(Parents parents);

  Optional<Parents> findByLoginId(String loginId);

  List<Parents> findByStudentId(Long id);
  Parents findByNameAndConnectionNumber(String name, String connectionNumber);
}

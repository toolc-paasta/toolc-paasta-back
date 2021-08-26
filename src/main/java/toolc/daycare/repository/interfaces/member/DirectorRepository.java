package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.member.Director;

import java.util.Optional;

public interface DirectorRepository {
    Director save(Director director);
    Optional <Director> findByLoginId(String loginId);

}

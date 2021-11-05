package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Center;

import java.util.Optional;

public interface CenterRepository {
    Center save(Center center);
    Center findByName(String name);
    Optional<Center> findByDirectorId(Long id);
}

package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Center;

import java.util.List;
import java.util.Optional;

public interface CenterRepository {
    Center save(Center center);
    Center findByName(String name);
    List<Center> findAll();
    Optional<Center> findByDirectorId(Long id);
}

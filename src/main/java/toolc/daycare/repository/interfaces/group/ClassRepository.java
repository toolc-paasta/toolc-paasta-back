package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Class;

import java.util.List;
import java.util.Optional;

public interface ClassRepository {
    Class save(Class aClass);
    Class findByNameAndCenterId(String name, Long centerId);
    List<Class> findByCenterId(Long centerId);
    Optional<Class> findById(Long id);
}

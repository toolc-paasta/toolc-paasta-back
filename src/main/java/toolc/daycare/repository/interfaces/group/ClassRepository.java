package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Class;

public interface ClassRepository {
    Class save(Class aClass);
    Class findByNameAndCenterId(String name, Long centerId);
}

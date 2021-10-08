package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Center;

public interface CenterRepository {
    Center save(Center center);

    Center findByName(String name);
    Center findByDirectorId(Long id);

}

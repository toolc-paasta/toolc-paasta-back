package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Center;

public interface CenterRepository {
    Center save(Center center);
    Center findByDirectorId(Long id);

}

package toolc.daycare.repository.interfaces.group;

import toolc.daycare.domain.group.Notice;

import java.util.List;

public interface NoticeRepository {
    Notice save(Notice notice);
    List<Notice> findByCenterId(Long centerId);
}

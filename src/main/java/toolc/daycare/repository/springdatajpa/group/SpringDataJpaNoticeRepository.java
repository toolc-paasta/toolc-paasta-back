package toolc.daycare.repository.springdatajpa.group;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.group.Notice;
import toolc.daycare.repository.interfaces.group.NoticeRepository;

public interface SpringDataJpaNoticeRepository extends JpaRepository<Notice, Long>, NoticeRepository {
}

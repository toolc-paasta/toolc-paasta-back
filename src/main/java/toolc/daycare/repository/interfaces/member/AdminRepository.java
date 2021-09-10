package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.member.Director;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional <Admin> findByLoginId(String loginId);
    List<Admin> findAll();
}

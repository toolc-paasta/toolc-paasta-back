package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class AdminService {

  private final MemberService memberService;
  private final AdminRepository adminRepository;
  private final CenterRegisterRepository registerRepository;

  @Autowired
  public AdminService(MemberService memberService,
                      AdminRepository adminRepository,
                      CenterRegisterRepository registerRepository) {
    this.memberService = memberService;
    this.adminRepository = adminRepository;
    this.registerRepository = registerRepository;
  }

  public Admin login(String loginId, String password, String expoToken) {
    Admin admin = findAdminByLoginId(loginId);
    memberService.checkLoginPassword(admin, password);

    admin.setExpoToken(expoToken);
    adminRepository.save(admin);

    return admin;
  }

  public List<CenterRegisterMessage> getAllRegister(){
    List<CenterRegisterMessage> registers = registerRepository.findAll();

    return registers;

  }

  public Admin findAdminByLoginId(String loginId) {
    return adminRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
  }


}

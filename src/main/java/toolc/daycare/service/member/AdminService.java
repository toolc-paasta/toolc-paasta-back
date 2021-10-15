package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.member.Director;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.AdminRepository;

import javax.transaction.Transactional;


@Slf4j
@Service
@Transactional
public class AdminService {

    private final MemberService memberService;
    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    @Autowired
    public AdminService(MemberService memberService,
                        AdminRepository adminRepository,
                        TokenService tokenService) {
        this.memberService = memberService;
        this.adminRepository = adminRepository;
        this.tokenService = tokenService;
    }

    public TokenVO login(String loginId, String password, String expoToken) {
        Admin admin = findAdminByLoginId(loginId);
        memberService.checkLoginPassword(admin, password);

        admin.setExpoToken(expoToken);

        AccessToken accessToken = tokenService.create(loginId, admin.getAuthority());

        return tokenService.formatting(accessToken);
    }


    public Admin findAdminByLoginId(String loginId){
        return adminRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
    }


}

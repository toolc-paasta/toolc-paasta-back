package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.repository.interfaces.member.AdminRepository;

import java.time.LocalDate;


@Slf4j
@Service
public class AdminService {

    private final MemberService memberService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(MemberService memberService,
                        AdminRepository adminRepository) {
        this.memberService = memberService;
        this.adminRepository = adminRepository;
    }

    public Admin login(String loginId, String password) {
        Admin admin = findAdminByLoginId(loginId);
        memberService.checkLoginPassword(admin, password);

        //토큰 설정 없어서 임시로 해놓음
        admin.setToken("tokenTest");
        adminRepository.save(admin);

        return admin;
    }


    public Admin findAdminByLoginId(String loginId){
        return adminRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
    }

//    public FcmSendBody centerRegister(String loginId, String centerName, List<String> targetUser, Map<String, String> data) {
//
//        Director director = directorRepository.findByLoginId(loginId)
//                .orElseThrow(NotExistMemberException::new);
//        String title = "Center 등록 신청";
//        String body = director.getName() + " 님의 " + centerName + "Center 등록 신청";
//
//        return fcmSender.sendFcmJson(/*director.getName(),*/ title, body, targetUser, data);
//
//    }
//
//
//    public Director findDirectorByLoginId(String loginId) {
//        return directorRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
//    }

}

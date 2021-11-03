package toolc.daycare.service.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Sex;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.exception.NotExistMemberException;
import toolc.daycare.fcm.FcmWebClient;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.member.DirectorRepository;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;
import toolc.daycare.repository.interfaces.message.TeacherRegisterClassRepository;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.fcm.FcmSender;
import toolc.daycare.authentication.AccessToken;
import toolc.daycare.authentication.TokenService;
import toolc.daycare.authentication.TokenVO;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class DirectorService {

    private final MemberService memberService;
    private final DirectorRepository directorRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final FcmSender fcmSender;
    private final TokenService tokenService;
    private final CenterRegisterRepository centerRegisterRepository;
    private final TeacherRegisterClassRepository teacherRegisterClassRepository;

    @Autowired
    public DirectorService(MemberService memberService,
                           DirectorRepository directorRepository,
                           AdminRepository adminRepository,
                           PasswordEncoder passwordEncoder,
                           FcmWebClient fcmWebClient,
                           FcmSender fcmSender,
                           TokenService tokenService,
                           CenterRegisterRepository centerRegisterRepository,
                           TeacherRegisterClassRepository teacherRegisterClassRepository) {
        this.memberService = memberService;
        this.adminRepository = adminRepository;
        this.directorRepository = directorRepository;
        this.passwordEncoder = passwordEncoder;
        this.fcmSender = fcmSender;
        this.tokenService = tokenService;
        this.centerRegisterRepository = centerRegisterRepository;
        this.teacherRegisterClassRepository = teacherRegisterClassRepository;
    }

    public Director signUp(String loginId, String password, String name, String connectionNumber, Sex sex) {
        memberService.checkDuplicateMember(loginId);
        Director director = Director.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .connectionNumber(connectionNumber)
                .sex(sex)
                .build();

        return directorRepository.save(director);
    }

    public TokenVO login(String loginId, String password, String expoToken) {
        Director director = directorRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        memberService.checkLoginPassword(director, password);

        director.setExpoToken(expoToken);
        directorRepository.save(director);

        AccessToken accessToken = tokenService.create(loginId);

        return tokenService.formatting(accessToken);
    }

    public FcmSendBody centerRegister(String loginId, String centerName, String address, LocalDate foundationDate) {

        // TODO : 메세지 보내는 사람도 필요하지 않을까? - DIRECTOR 불러줘야 할 것인가?
        Director director = directorRepository.findByLoginId(loginId)
                .orElseThrow(NotExistMemberException::new);
        String title = "Center 등록 신청";
        String body = director.getName() + " 님의 " + centerName + " Center 등록 신청";

        List<String> targetUser = new LinkedList<>();
        adminRepository.findAll().forEach(e -> targetUser.add(e.getLoginId()));

        Map<String, Object> data = new HashMap<>();
        data.put("centerName", centerName);
        data.put("address", address);
        data.put("foundationDate", foundationDate);

        CenterRegisterMessage message = new CenterRegisterMessage(director, centerName, address,
          foundationDate);
        centerRegisterRepository.save(message);

        // TODO : 메세지 보내는 사람도 필요하지 않을까?
        return fcmSender.sendFcmJson(/*director.getName(),*/ title, body, targetUser, data);

    }

    public List<TeacherRegisterClassMessage> findAllRegisterRequest(){

        return teacherRegisterClassRepository.findAll();
    }


    public Director findDirectorByLoginId(String loginId) {
        return directorRepository.findByLoginId(loginId).orElseThrow(NotExistMemberException::new);
    }

}

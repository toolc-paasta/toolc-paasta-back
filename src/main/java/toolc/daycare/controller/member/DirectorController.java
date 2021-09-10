package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.director.CenterRegisterRequestDto;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.dto.member.response.director.DirectorLoginResponseDto;
import toolc.daycare.dto.member.response.director.DirectorRegisterCenterDto;
import toolc.daycare.dto.member.response.director.DirectorSignupResponseDto;
import toolc.daycare.service.fcm.FcmSendBody;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/member/director")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody DirectorSignupRequestDto directorSignupRequestDto) {
        RequestUtil.checkNeedValue(
                directorSignupRequestDto.getLoginId(),
                directorSignupRequestDto.getPassword(),
                directorSignupRequestDto.getName(),
                directorSignupRequestDto.getConnectionNumber()
        );
        RequestUtil.checkCorrectEnum(
                directorSignupRequestDto.getSex()
        );


        Director newDirector = directorService.signUp(
                directorSignupRequestDto.getLoginId(),
                directorSignupRequestDto.getPassword(),
                directorSignupRequestDto.getName(),
                directorSignupRequestDto.getConnectionNumber(),
                directorSignupRequestDto.getSex()
        );

        BaseResponseSuccessDto responseBody = new DirectorSignupResponseDto(newDirector);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signUp(@RequestBody LoginRequestDto loginRequestDto) {
        RequestUtil.checkNeedValue(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        Director loginDirector = directorService.login(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        BaseResponseSuccessDto responseBody = new DirectorLoginResponseDto(loginDirector);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/centerRegister")
    public ResponseEntity<?> centerRegister(@RequestBody CenterRegisterRequestDto centerRegisterDto) {
        RequestUtil.checkNeedValue(
                centerRegisterDto.getCenterName(),
                centerRegisterDto.getCenterName(),
                centerRegisterDto.getAddress(),
                centerRegisterDto.getFoundationDate()
        );

        FcmSendBody fcm = directorService.centerRegister(centerRegisterDto.getLoginId(),
                centerRegisterDto.getCenterName(),
                centerRegisterDto.getAddress(),
                centerRegisterDto.getFoundationDate());

        BaseResponseSuccessDto responseBody = new DirectorRegisterCenterDto(fcm);
        return ResponseEntity.ok(responseBody);
    }
}

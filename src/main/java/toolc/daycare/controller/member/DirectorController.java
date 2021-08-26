package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.director.DirectorSignupRequestDto;
import toolc.daycare.dto.member.response.director.DirectorLoginResponseDto;
import toolc.daycare.dto.member.response.director.DirectorSignupResponseDto;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;

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
                directorSignupRequestDto.getName()
        );
        RequestUtil.checkCorrectEnum(
                directorSignupRequestDto.getSex()
        );


        Director newDirector = directorService.signUp(
                directorSignupRequestDto.getLoginId(),
                directorSignupRequestDto.getPassword(),
                directorSignupRequestDto.getName(),
                directorSignupRequestDto.getSex()
                );

        BaseResponseSuccessDto responseBody = new DirectorSignupResponseDto(newDirector);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signUp(@RequestBody LoginRequestDto loginRequestDto){
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
}

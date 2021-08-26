package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.dto.member.parents.ParentsSignupRequestDto;
import toolc.daycare.service.member.ParentsService;
import toolc.daycare.util.RequestUtil;

@Slf4j
@RestController
@RequestMapping("/api/member/parents")
public class ParentsController {

    private final ParentsService parentsService;

    @Autowired
    public ParentsController(ParentsService parentsService) {
        this.parentsService = parentsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody ParentsSignupRequestDto parentsSignupRequestDto) {
        RequestUtil.checkNeedValue(
                parentsSignupRequestDto.getLoginId(),
                parentsSignupRequestDto.getPassword(),
                parentsSignupRequestDto.getName(),
                parentsSignupRequestDto.getChildName(),
                parentsSignupRequestDto.getChildBirthday()
        );
        RequestUtil.checkCorrectEnum(
                parentsSignupRequestDto.getSex(),
                parentsSignupRequestDto.getChildSex()
        );

        Parents newParents = parentsService.signUp(
                parentsSignupRequestDto.getLoginId(),
                parentsSignupRequestDto.getPassword(),
                parentsSignupRequestDto.getName(),
                parentsSignupRequestDto.getSex(),
                parentsSignupRequestDto.getChildName(),
                parentsSignupRequestDto.getChildBirthday(),
                parentsSignupRequestDto.getChildSex()
                );

        return ResponseEntity.ok(newParents);
    }
}

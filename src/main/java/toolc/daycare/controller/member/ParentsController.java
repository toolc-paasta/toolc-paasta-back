package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.parents.ParentsSignupRequestDto;
import toolc.daycare.service.member.ParentsService;
import toolc.daycare.util.RequestUtil;
import toolc.daycare.vo.ParentVO;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/member/parents")
public class ParentsController {

  private final ParentsService parentsService;

  @Autowired
  public ParentsController(ParentsService parentsService) {
    this.parentsService = parentsService;
  }

  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Parents parents = parentsService.findParentsByLoginId(loginId);

    ResponseDto<Parents> response = new ResponseDto<>(OK.value(), "정보 조회 성공", parents);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody ParentsSignupRequestDto parentsSignupRequestDto) {
    RequestUtil.checkNeedValue(
      parentsSignupRequestDto.getLoginId(),
      parentsSignupRequestDto.getPassword(),
      parentsSignupRequestDto.getName(),
      parentsSignupRequestDto.getConnectionNumber(),
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
      parentsSignupRequestDto.getConnectionNumber(),
      parentsSignupRequestDto.getChildName(),
      parentsSignupRequestDto.getChildBirthday(),
      parentsSignupRequestDto.getChildSex()
    );

    ResponseDto<Parents> responseBody = new ResponseDto<>(OK.value(), "회원가입 성공", newParents);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    RequestUtil.checkNeedValue(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword()
    );

    TokenVO token = parentsService.login(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword(),
      loginRequestDto.getExpoToken()
    );

    ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "로그인 성공", token);
    return ResponseEntity.ok(responseBody);
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchParent(@RequestParam("name") String name, @RequestParam("connectionNumber") String connectionNumber) {
    RequestUtil.checkNeedValue(
      name,
      connectionNumber
    );

    ParentVO parent = parentsService.search(name, connectionNumber);

    ResponseDto<ParentVO> responseBody = new ResponseDto<>(OK.value(), "검색 성공", parent);
    return ResponseEntity.ok(responseBody);
  }
}

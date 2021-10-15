package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toolc.daycare.authentication.Auth;
import toolc.daycare.authentication.TokenVO;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.dto.group.request.center.CenterRegisterRequestDto;
import toolc.daycare.dto.group.response.center.CenterRegisterResponseDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.response.admin.AdminLoginResponseDto;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.member.AdminService;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/member/admin")
public class AdminController {

  private final AdminService adminService;
  private final DirectorService directorService;
  private final CenterService centerService;

  @Autowired
  public AdminController(AdminService adminService,
                         DirectorService directorService,
                         CenterService centerService
  ) {
    this.adminService = adminService;
    this.directorService = directorService;
    this.centerService = centerService;
  }

  @GetMapping
  public ResponseEntity<?> getInfo(@Auth String loginId) {
    Admin admin = adminService.findAdminByLoginId(loginId);

    ResponseDto<Admin> response = new ResponseDto<>(OK.value(), "정보 조회 성공", admin);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    RequestUtil.checkNeedValue(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword()
    );

    TokenVO token = adminService.login(
      loginRequestDto.getLoginId(),
      loginRequestDto.getPassword(),
      loginRequestDto.getExpoToken()
    );

    ResponseDto<TokenVO> responseBody = new ResponseDto<>(OK.value(), "로그인 성공", token);
    return ResponseEntity.ok(responseBody);
  }

  @PostMapping("allowCenter")
  public ResponseEntity<?> allowCenter(@Auth String loginId, @RequestBody CenterRegisterRequestDto centerRegisterRequestDto) {

    //TODO : 나중에 권한으로 바꿔줘야함
    Admin admin = adminService.findAdminByLoginId(loginId);

    RequestUtil.checkNeedValue(
      centerRegisterRequestDto.getDirectorLoginId(),
      centerRegisterRequestDto.getCenterName(),
      centerRegisterRequestDto.getAddress(),
      centerRegisterRequestDto.getFoundationDate()
    );

    Director director = directorService.findDirectorByLoginId(centerRegisterRequestDto.getDirectorLoginId());

    Center newCenter = centerService.register(
      director,
      centerRegisterRequestDto.getCenterName(),
      centerRegisterRequestDto.getAddress(),
      centerRegisterRequestDto.getFoundationDate());

    ResponseDto<Center> responseBody = new ResponseDto<>(OK.value(), "요청 수락", newCenter);
    return ResponseEntity.ok(responseBody);
  }

}

package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.domain.member.Director;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.group.request.center.CenterRegisterRequestDto;
import toolc.daycare.dto.group.response.center.CenterRegisterResponseDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.response.admin.AdminLoginResponseDto;
import toolc.daycare.service.CenterService;
import toolc.daycare.service.member.AdminService;
import toolc.daycare.service.member.DirectorService;
import toolc.daycare.util.RequestUtil;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        RequestUtil.checkNeedValue(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        Admin loginAdmin = adminService.login(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        BaseResponseSuccessDto responseBody = new AdminLoginResponseDto(loginAdmin);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("allowCenter")
    public ResponseEntity<?> allowCenter(@RequestBody CenterRegisterRequestDto centerRegisterRequestDto) {

        //TODO : 나중에 권한으로 바꿔줘야함
        Admin admin = adminService.findAdminByLoginId(centerRegisterRequestDto.getLoginId());

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

        BaseResponseSuccessDto responseBody = new CenterRegisterResponseDto(newCenter);
        return ResponseEntity.ok(responseBody);
    }

}

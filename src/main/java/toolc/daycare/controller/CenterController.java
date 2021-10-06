//package toolc.daycare.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import toolc.daycare.domain.group.Center;
//import toolc.daycare.domain.member.Director;
//import toolc.daycare.dto.BaseResponseSuccessDto;
//import toolc.daycare.dto.group.request.center.CenterRegisterRequestDto;
//import toolc.daycare.dto.group.response.center.CenterRegisterResponseDto;
//import toolc.daycare.service.CenterService;
//import toolc.daycare.service.member.DirectorService;
//import toolc.daycare.util.RequestUtil;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/center")
//public class CenterController {
//    private final CenterService centerService;
//    private final DirectorService directorService;

//    @Autowired
//    public CenterController(CenterService centerService,
//                            DirectorService directorService) {
//        this.centerService = centerService;
//        this.directorService = directorService;
//    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody CenterRegisterRequestDto centerRegisterRequestDto) {
//        RequestUtil.checkNeedValue(
//                centerRegisterRequestDto.getDirectorLoginId(),
//                centerRegisterRequestDto.getName(),
//                centerRegisterRequestDto.getAddress(),
//                centerRegisterRequestDto.getFoundationDate(),
//                centerRegisterRequestDto.getStar()
//        );
//        Director director = directorService.findDirectorByLoginId(centerRegisterRequestDto.getDirectorLoginId());
//
//        Center newCenter = centerService.register(
//                director,
//                centerRegisterRequestDto.getName(),
//                centerRegisterRequestDto.getAddress(),
//                centerRegisterRequestDto.getFoundationDate(),
//                centerRegisterRequestDto.getStar());
//
//        BaseResponseSuccessDto responseBody = new CenterRegisterResponseDto(newCenter);
//        return ResponseEntity.ok(responseBody);
//    }

//    @GetMapping("/search")
//    public ResponseEntity<?> search(@RequestBody ){
//        centerService.
//    }
//}

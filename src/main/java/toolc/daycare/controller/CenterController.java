package toolc.daycare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.authentication.Auth;
import toolc.daycare.dto.ResponseDto;
import toolc.daycare.mapper.CenterMapper;
import toolc.daycare.service.CenterService;
import toolc.daycare.vo.CenterDetailVO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/center")
public class CenterController {
  private final CenterService centerService;
  private final CenterMapper mapper = new CenterMapper();

  @GetMapping
  public ResponseEntity<?> getAllCenter() {
    List<CenterDetailVO> centerDetailVOList = centerService.getAllCenter()
      .stream()
      .map(c -> mapper.toCenterDetailVo(c, centerService.getAllClassByCenterId(c.getId())))
      .collect(Collectors.toList());

    ResponseDto<List<CenterDetailVO>> responseBody = new ResponseDto<>(OK.value(), "조회 성공", centerDetailVOList);
    return ResponseEntity.ok(responseBody);
  }
}
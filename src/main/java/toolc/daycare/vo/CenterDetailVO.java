package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class CenterDetailVO {
  String name;
  String address;
  String channelName;
  String directorName;
  String directorLoginId;
  LocalDate foundationDate;
  Long star;
  List<ClassVO> classVOList;

  @Builder
  public CenterDetailVO(
    String name,
    String address,
    String channelName,
    String directorName,
    String directorLoginId,
    LocalDate foundationDate,
    Long star,
    List<ClassVO> classVOList) {
    this.name = name;
    this.address = address;
    this.channelName = channelName;
    this.directorName = directorName;
    this.directorLoginId = directorLoginId;
    this.foundationDate = foundationDate;
    this.star = star;
    this.classVOList = classVOList;
  }
}

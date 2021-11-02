package toolc.daycare.vo;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CenterVO {
  String name;
  String address;
  String channelName;
  String directorName;
  LocalDate foundationDate;
  Long star;

  @Builder
  public CenterVO(String name, String address, String channelName, String directorName, LocalDate foundationDate, Long star) {
    this.name = name;
    this.address = address;
    this.channelName = channelName;
    this.directorName = directorName;
    this.foundationDate = foundationDate;
    this.star = star;
  }
}

package toolc.daycare.authentication;

import lombok.Value;

@Value
public class TokenVO {
  String accessToken;

  public TokenVO(String accessToken) {
    this.accessToken = accessToken;
  }
}

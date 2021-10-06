package toolc.daycare.token;

import lombok.Value;

import java.time.Instant;

@Value
public class AccessToken {
  String loginId;
  Instant expirationAt;

  public AccessToken(String loginId, Instant expirationAt) {
    this.loginId = loginId;
    this.expirationAt = expirationAt;
  }
}

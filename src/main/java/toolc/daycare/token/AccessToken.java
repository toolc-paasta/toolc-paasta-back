package toolc.daycare.token;

import lombok.Value;
import toolc.daycare.token.time.CurrentTimeServer;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static toolc.daycare.token.AccessToken.GrantType.BEARER;

@Value
public class AccessToken {
  enum GrantType {
    BEARER
  }
  String loginId;
  final static Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofDays(30);
  Instant expirationAt;
  GrantType grantType = BEARER;

  public AccessToken(String loginId, Instant expirationAt) {
    this.loginId = loginId;
    this.expirationAt = expirationAt;
  }

  public static AccessToken issue(String loginId, CurrentTimeServer currentTimeServer) {
    return new AccessToken(loginId, currentTimeServer.now().truncatedTo(ChronoUnit.SECONDS).plus(ACCESS_TOKEN_EXPIRE_TIME));
  }
}

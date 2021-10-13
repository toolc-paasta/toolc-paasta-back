package toolc.daycare.authentication;

import lombok.Value;
import toolc.daycare.authentication.time.CurrentTimeServer;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static toolc.daycare.authentication.AccessToken.GrantType.Bearer;

@Value
public class AccessToken {
  enum GrantType {
    Bearer
  }
  String loginId;
  final static Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofDays(30);
  Instant expirationAt;
  GrantType grantType = Bearer;

  public AccessToken(String loginId, Instant expirationAt) {
    this.loginId = loginId;
    this.expirationAt = expirationAt;
  }

  public static AccessToken issue(String loginId, CurrentTimeServer currentTimeServer) {
    return new AccessToken(loginId, currentTimeServer.now().truncatedTo(ChronoUnit.SECONDS).plus(ACCESS_TOKEN_EXPIRE_TIME));
  }
}

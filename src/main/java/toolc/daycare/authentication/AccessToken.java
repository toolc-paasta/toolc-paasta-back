package toolc.daycare.authentication;

import lombok.Value;
import toolc.daycare.authentication.time.CurrentTimeServer;
import toolc.daycare.domain.member.Authority;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static toolc.daycare.authentication.AccessToken.GrantType.BEARER;

@Value
public class AccessToken {
  enum GrantType {
    BEARER
  }
  String loginId;
  final static Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofDays(30);
  Instant expirationAt;
  GrantType grantType = BEARER;
  final static String AUTHORITY_KEY = "auth";
  Authority authority;

  public AccessToken(String loginId, Instant expirationAt, Authority authority) {
    this.loginId = loginId;
    this.expirationAt = expirationAt;
    this.authority = authority;
  }

  public static AccessToken issue(String loginId, CurrentTimeServer currentTimeServer, Authority authority) {
    return new AccessToken(loginId, currentTimeServer.now().truncatedTo(ChronoUnit.SECONDS).plus(ACCESS_TOKEN_EXPIRE_TIME), authority);
  }
}

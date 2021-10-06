package toolc.daycare;

import toolc.daycare.token.AccessToken;
import toolc.daycare.token.time.ConstantTime;
import toolc.daycare.token.time.CurrentTimeServer;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

public class Fixture {
  public static CurrentTimeServer time() {
    return new ConstantTime(LocalDateTime.of(1998, 02, 25, 12, 12).toInstant(UTC));
  }

  public static AccessToken accessToken(String loginId) {
    return AccessToken.of(loginId, time());
  }
}

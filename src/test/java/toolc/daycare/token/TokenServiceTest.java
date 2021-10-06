package toolc.daycare.token;

import org.junit.jupiter.api.Test;
import toolc.daycare.Matchers;

import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static toolc.daycare.Fixture.*;
import static toolc.daycare.Matchers.isAfter30MinutesFrom;

class TokenServiceTest {
  TokenService tokenService = new TokenService(time(), jwtFormatter());

  @Test
  void 토큰_만료시간_검사() {
    String loginId = "test001";

    AccessToken accessToken = tokenService.create(loginId);

    assertThat(accessToken, isAfter30MinutesFrom(time().now()));
  }
}
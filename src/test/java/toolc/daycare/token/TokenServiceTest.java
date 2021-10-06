package toolc.daycare.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static toolc.daycare.Fixture.accessToken;
import static toolc.daycare.Fixture.time;

class TokenServiceTest {
  TokenService tokenService = new TokenService(time());

  @Test
  void 토큰_만료시간_검사() {
    String loginId = "test001";

    AccessToken accessToken = tokenService.create(loginId);

    assertThat(Duration.between(time().now(), accessToken.getExpirationAt()), is(AccessToken.ACCESS_TOKEN_EXPIRE_TIME));
  }
}
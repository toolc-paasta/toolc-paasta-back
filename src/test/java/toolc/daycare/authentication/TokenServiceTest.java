package toolc.daycare.authentication;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static toolc.daycare.Fixture.*;
import static toolc.daycare.Matchers.isAfter30MinutesFrom;

class TokenServiceTest {
  TokenService tokenService = new TokenService(time(), jwtFormatter(), tokenParser());

  @Test
  void 토큰_만료시간_검사() {
    String loginId = "test001";

    AccessToken accessToken = tokenService.create(loginId, AUTHORITY);

    assertThat(accessToken, isAfter30MinutesFrom(time().now()));
  }

  @Test
  void 토큰_파싱_포맷팅_검사() {
    String loginId = "test001";

    AccessToken accessToken = accessToken(loginId, realTime());
    TokenVO tokenVO = tokenService.formatting(accessToken);
    System.out.println(tokenVO.getAccessToken());
    AccessToken parsedToken = tokenService.parse(tokenVO.getAccessToken());

    assertThat(accessToken, is(parsedToken));
  }
}
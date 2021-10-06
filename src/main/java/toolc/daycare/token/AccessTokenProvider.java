package toolc.daycare.token;


import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class AccessTokenProvider {
  final Period ACCESS_TOKEN_EXPIRE_TIME = Period.ofMonths(1);

  AccessToken create(String loginId) {
    return new AccessToken(loginId, Instant.now().truncatedTo(ChronoUnit.SECONDS).plus(ACCESS_TOKEN_EXPIRE_TIME));
  }
}

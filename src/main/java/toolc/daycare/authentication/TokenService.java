package toolc.daycare.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toolc.daycare.authentication.time.CurrentTimeServer;
import toolc.daycare.domain.member.Authority;

@RequiredArgsConstructor
@Service
public class TokenService {
  final CurrentTimeServer currentTimeServer;
  final JwtFormatter jwtFormatter;
  final TokenParser tokenParser;

  public AccessToken create(String loginId, Authority authority) {
    return AccessToken.issue(loginId, currentTimeServer, authority);
  }

  public TokenVO formatting(AccessToken accessToken) {
    return jwtFormatter.toJwt(accessToken);
  }

  public AccessToken parse(String tokenValue) {
    return tokenParser.parse(tokenValue);
  }
}

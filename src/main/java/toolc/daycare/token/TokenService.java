package toolc.daycare.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toolc.daycare.token.time.CurrentTimeServer;

@RequiredArgsConstructor
@Service
public class TokenService {
  final CurrentTimeServer currentTimeServer;
  final JwtFormatter jwtFormatter;
  final TokenParser tokenParser;

  public AccessToken create(String loginId) {
    return AccessToken.issue(loginId, currentTimeServer);
  }

  public TokenVO formatting(AccessToken accessToken) {
    return jwtFormatter.toJwt(accessToken);
  }

  public AccessToken parse(String tokenValue) {
    return tokenParser.parse(tokenValue);
  }
}

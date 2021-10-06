package toolc.daycare.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toolc.daycare.token.config.JwtSetConfig;

import java.time.Instant;

@Component
public class TokenParser {
  final JwtParser jwtParser;
  final JwtSetConfig jwtSetConfig;

  @Autowired
  public TokenParser(JwtSetConfig jwtSetConfig) {
    this.jwtSetConfig = jwtSetConfig;
    this.jwtParser = Jwts.parserBuilder()
      .setSigningKey(jwtSetConfig.getSecretKey())
      .build();
  }

  public AccessToken parse(String tokenValue) {
    final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(tokenValue);
    final Claims claims = claimsJws.getBody();
    final String loginId = claims.getSubject();
    final Instant expiration = claims.getExpiration().toInstant();

    return new AccessToken(loginId, expiration);
  }
}

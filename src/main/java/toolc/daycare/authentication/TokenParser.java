package toolc.daycare.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toolc.daycare.authentication.config.JwtSetConfig;
import toolc.daycare.domain.member.Authority;

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
    final Authority authority = Authority.valueOf(claims.get(AccessToken.AUTHORITY_KEY).toString());

    return new AccessToken(loginId, expiration, authority);
  }
}

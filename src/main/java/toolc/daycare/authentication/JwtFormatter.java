package toolc.daycare.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toolc.daycare.authentication.config.JwtSetConfig;

import javax.crypto.SecretKey;
import java.sql.Timestamp;

import static toolc.daycare.authentication.AccessToken.AUTHORITY_KEY;

@Component
@RequiredArgsConstructor
public class JwtFormatter {
  private final JwtSetConfig jwtConfig;

  public TokenVO toJwt(AccessToken accessToken) {
    final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecretKey()));

    String accessTokenValue = Jwts.builder()
      .setSubject(accessToken.getLoginId())
      .setExpiration(Timestamp.from(accessToken.getExpirationAt()))
      .signWith(key, jwtConfig.getSignatureAlgorithm())
      .claim(AUTHORITY_KEY, accessToken.getAuthority())
      .compact();

    return new TokenVO(accessTokenValue);
  }
}

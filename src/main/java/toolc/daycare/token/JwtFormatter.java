package toolc.daycare.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class JwtFormatter {
  private final JwtSetConfig jwtConfig;

  TokenVO toJwt(AccessToken accessToken) {
    final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecretKey()));

    String accessTokenValue = Jwts.builder()
      .setSubject(accessToken.getLoginId())
      .setExpiration(Timestamp.from(accessToken.getExpirationAt()))
      .signWith(key, jwtConfig.getSignatureAlgorithm())
      .compact();

    return new TokenVO(accessTokenValue);
  }
}

package toolc.daycare.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import toolc.daycare.authentication.config.JwtSetConfig;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

import static toolc.daycare.authentication.AccessToken.GrantType.BEARER;

@Component
public class TokenResolver {
  private final SecretKey key;
  private static final String AUTHORIZATION_HEADER = "Authorization";

  public TokenResolver(JwtSetConfig jwtSetConfig) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSetConfig.getSecretKey()));
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);
    Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

    UserDetails principal = new User(claims.getSubject(), "",  authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public boolean validateToken(String token) throws Exception {
    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    return true;
  }

  public String resolveRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER.toString())) {
      return bearerToken.substring(BEARER.toString().length());
    }
    return null;
  }

  private Claims parseClaims(String accessToken) throws ExpiredJwtException {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
  }
}

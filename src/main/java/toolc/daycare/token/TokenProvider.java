package toolc.daycare.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProvider {
 final AccessTokenProvider accessTokenProvider;

 public Token create(String loginId) {
   AccessToken accessToken = accessTokenProvider.create(loginId);

   return new Token(accessToken);
 }
}
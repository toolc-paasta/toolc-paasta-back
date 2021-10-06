package toolc.daycare.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toolc.daycare.token.time.CurrentTimeServer;

@RequiredArgsConstructor
@Service
public class TokenService {
  final CurrentTimeServer currentTimeServer;

  public AccessToken create(String loginId) {
    return AccessToken.of(loginId, currentTimeServer);
  }
}

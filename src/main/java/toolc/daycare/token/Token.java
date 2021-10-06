package toolc.daycare.token;

import lombok.Value;

import static toolc.daycare.token.Token.GrantType.BEARER;

@Value
public class Token {
  enum GrantType {
    BEARER;
  }
  GrantType grantType;
  AccessToken accessToken;

  public Token(AccessToken accessToken) {
    this.accessToken = accessToken;
    this.grantType = BEARER;
  }
}

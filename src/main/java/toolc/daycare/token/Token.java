package toolc.daycare.token;

import lombok.Value;

@Value
public class Token {
  enum GrantType {
    BEARER;
  }
  GrantType grantType;
  AccessToken accessToken;

  public Token(GrantType grantType, AccessToken accessToken) {
    this.accessToken = accessToken;
    this.grantType = grantType;
  }
}

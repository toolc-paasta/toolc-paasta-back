package toolc.daycare.token;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;

@Value
public class JwtSetConfig {
  String secretKey;
  SignatureAlgorithm signatureAlgorithm;

  public JwtSetConfig(String secretKey, SignatureAlgorithm signatureAlgorithm) {
    this.secretKey = secretKey;
    this.signatureAlgorithm = signatureAlgorithm;
  }
}

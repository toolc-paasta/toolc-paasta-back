package toolc.daycare.token.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSetConfigYaml {
  @Value("${jwt.secretKey}")
  private String secretKey;
  @Value("${jwt.algorithm}")
  private SignatureAlgorithm signatureAlgorithm;

  public JwtSetConfig toJwtSetConfig() {
    return new JwtSetConfig(secretKey, signatureAlgorithm);
  }
}

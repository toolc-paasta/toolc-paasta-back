package toolc.daycare.token.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toolc.daycare.token.time.CurrentTimeServer;
import toolc.daycare.token.time.RealTime;

@Configuration
@RequiredArgsConstructor
public class TokenConfig {
  private final JwtSetConfigYaml jwtSetConfigYaml;

  @Bean
  public CurrentTimeServer currentTimeServer() {
    return new RealTime();
  }

  @Bean
  public JwtSetConfig jwtSetConfig() {
    return jwtSetConfigYaml.toJwtSetConfig();
  }
}

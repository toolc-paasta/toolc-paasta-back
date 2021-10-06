package toolc.daycare.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toolc.daycare.authentication.time.CurrentTimeServer;
import toolc.daycare.authentication.time.RealTime;

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

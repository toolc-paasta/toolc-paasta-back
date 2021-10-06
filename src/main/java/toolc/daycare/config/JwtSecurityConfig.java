package toolc.daycare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import toolc.daycare.token.TokenResolver;
import toolc.daycare.token.config.JwtSetConfig;
import toolc.daycare.token.config.JwtSetConfigYaml;
import toolc.daycare.token.filter.ExceptionHandlerFilter;
import toolc.daycare.token.filter.TokenFilter;
import toolc.daycare.token.time.CurrentTimeServer;
import toolc.daycare.token.time.RealTime;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtSetConfigYaml jwtSetConfigYaml;
  private final TokenResolver tokenResolver;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    TokenFilter customFilter = new TokenFilter(tokenResolver);
    ExceptionHandlerFilter exceptionHandlerFilter = new ExceptionHandlerFilter();
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(exceptionHandlerFilter, TokenFilter.class);
  }

  @Bean
  public CurrentTimeServer currentTimeServer() {
    return new RealTime();
  }

  @Bean
  public JwtSetConfig jwtSetConfig() {
    return jwtSetConfigYaml.toJwtSetConfig();
  }
}

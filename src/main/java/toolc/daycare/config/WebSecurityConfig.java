package toolc.daycare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import toolc.daycare.token.TokenResolver;
import toolc.daycare.token.config.JwtSetConfig;
import toolc.daycare.token.config.JwtSetConfigYaml;
import toolc.daycare.token.handler.JwtAccessDeniedHandler;
import toolc.daycare.token.handler.JwtAuthenticationEntryPoint;
import toolc.daycare.token.time.CurrentTimeServer;
import toolc.daycare.token.time.RealTime;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenResolver resolver;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .accessDeniedHandler(jwtAccessDeniedHandler)
      .and()

      .headers()
      .frameOptions()
      .disable()
      .and()

      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()

      .authorizeRequests()
      .antMatchers("/**").permitAll()
      .and()

      .cors()
      .and()

      .apply(new JwtSecurityConfig(resolver));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}

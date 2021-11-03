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
import org.springframework.web.cors.CorsUtils;
import toolc.daycare.authentication.TokenResolver;
import toolc.daycare.authentication.handler.JwtAccessDeniedHandler;
import toolc.daycare.authentication.handler.JwtAuthenticationEntryPoint;

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
      .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
      .antMatchers("/h2-console/**").permitAll()
      // ADMIN 권한
      .antMatchers("/api/member/admin/allowCenter").hasAuthority("ADMIN")

      // DIRECTOR 권한
      .antMatchers("/api/member/director/centerRegister").hasAuthority("DIRECTOR")
      .antMatchers("/api/member/director/create/class").hasAuthority("DIRECTOR")

      // TEACHER 권한
      .antMatchers("/api/member/teacher/read").hasAuthority("TEACHER")

      // PARENT 권한
      // 추가예정

      .antMatchers("/api/member/director").authenticated()
      .antMatchers("/api/member/**").permitAll()
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

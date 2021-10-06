package toolc.daycare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import toolc.daycare.token.TokenResolver;
import toolc.daycare.token.filter.ExceptionHandlerFilter;
import toolc.daycare.token.filter.TokenFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final TokenResolver tokenResolver;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    TokenFilter customFilter = new TokenFilter(tokenResolver);
    ExceptionHandlerFilter exceptionHandlerFilter = new ExceptionHandlerFilter();
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(exceptionHandlerFilter, TokenFilter.class);
  }
}

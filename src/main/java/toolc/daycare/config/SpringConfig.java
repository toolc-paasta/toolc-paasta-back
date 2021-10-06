package toolc.daycare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toolc.daycare.authentication.AuthenticationArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {
  private final AuthenticationArgumentResolver argumentResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(argumentResolver);
  }
}

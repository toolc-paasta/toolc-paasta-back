package toolc.daycare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import toolc.daycare.token.JwtSetConfig;
import toolc.daycare.token.JwtSetConfigYaml;
import toolc.daycare.token.time.CurrentTimeServer;
import toolc.daycare.token.time.RealTime;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final JwtSetConfigYaml jwtSetConfigYaml;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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

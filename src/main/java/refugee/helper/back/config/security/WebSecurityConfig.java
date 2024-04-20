package refugee.helper.back.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refugee.helper.back.service.api.JwtService;
import refugee.helper.back.service.api.UserService;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
  private final UserService userService;
  private final JwtService jwtService;
  private final FilterChainExceptionHandler filterChainExceptionHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/a/rest/volunteer/**")
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf()
        .disable()
        .addFilterBefore(
            new JwtAuthorizationFilter(userService, jwtService),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterChainExceptionHandler, JwtAuthorizationFilter.class);
    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) ->
        web.ignoring()
            .requestMatchers(
                "/images/**",
                "/a/rest/volunteer/account/register",
                "/a/rest/volunteer/account/login",
                "/a/swagger-ui/**",
                "/a/rest/refugee/**",
                "/a/v3/api-docs/**");
  }
}

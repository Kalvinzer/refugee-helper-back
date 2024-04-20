package refugee.helper.back.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import refugee.helper.back.service.api.JwtService;
import refugee.helper.back.service.api.UserService;
import refugee.helper.back.service.model.SecurityUserModel;
import refugee.helper.back.service.model.SecurityUserModel.Roles;
import refugee.helper.back.web.exceptions.model.IncorrectCredentialsException;
import refugee.helper.back.web.exceptions.model.JwtAuthenticationException;
import refugee.helper.back.web.exceptions.model.UserNotActivatedException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
  private static final String HEADER = "Authorization";
  private static final String PREFIX = "Bearer ";
  private final UserService userService;
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    getAndAuthenticateUserFromJwt(getAndValidateJwtToken(request));
    chain.doFilter(request, response);
  }

  private String getAndValidateJwtToken(HttpServletRequest request) {
    String authenticationHeader = request.getHeader(HEADER);
    if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
      throw new JwtAuthenticationException(
          Map.of("token", "Необхідно надати токен авторизації"), "Jwt error");
    }
    String jwt = authenticationHeader.replace(PREFIX, "");
    return jwt;
  }

  private void getAndAuthenticateUserFromJwt(String jwt) {
    try {
      SecurityUserModel securityUserModel =
          userService.getUserByEmail(jwtService.getEmailFromJwt(jwt));
      securityUserModel.setRole(Roles.ROLE_VOLUNTEER);

      if (!securityUserModel.isEnabled()) {
        throw new UserNotActivatedException(
            Map.of("email", "Будь-ласка зв'яжіться з адміністрацією для активації акаунту"),
            "User not activated");
      }
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
              securityUserModel, "", securityUserModel.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (IncorrectCredentialsException e) {
      throw new JwtAuthenticationException(
          Map.of("token", "Помилка перевірки токену"), "Jwt error");
    }
  }
}

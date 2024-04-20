package refugee.helper.back.service.impl;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Service;
import refugee.helper.back.service.api.JwtService;
import refugee.helper.back.web.exceptions.model.JwtAuthenticationException;

@Service
public class JwtServiceImpl implements JwtService {
  private final byte[] secretKey =
      "3D6FAEBD94B4FF9CA5C00376DD752F47".getBytes(StandardCharsets.UTF_8);
  private final ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor =
      new DefaultJWTProcessor<>();

  @PostConstruct
  public void init() {
    JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<>(secretKey);
    JWEKeySelector<SimpleSecurityContext> jweKeySelector =
        new JWEDecryptionKeySelector<>(
            JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
    jwtProcessor.setJWEKeySelector(jweKeySelector);
  }

  @Override
  public String createJwt(String email) {
    try {
      Date now = new Date();
      Date expDate = new Date(now.getTime() + 1000 * 60 * 60 * 24 * 10); // expires in 10 days

      JWTClaimsSet claims =
          new JWTClaimsSet.Builder()
              .claim("email", email)
              .expirationTime(expDate)
              .notBeforeTime(now)
              .build();

      Payload payload = new Payload(claims.toJSONObject());

      JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);

      DirectEncrypter encrypter = new DirectEncrypter(secretKey);

      JWEObject jweObject = new JWEObject(header, payload);
      jweObject.encrypt(encrypter);
      return jweObject.serialize();
    } catch (JOSEException e) {
      throw new JwtAuthenticationException(
          Map.of("token", "Помилка при створенні токену"), "Jwt unknown error");
    }
  }

  @Override
  public String getEmailFromJwt(String jwt) {
    return (String) getJwtClaimsSetFromToken(jwt).getClaim("email");
  }

  private JWTClaimsSet getJwtClaimsSetFromToken(String jwt) {
    try {
      return jwtProcessor.process(jwt, null);
    } catch (BadJOSEException e) {
      throw new JwtAuthenticationException(
          Map.of(
              "token",
              (e.getMessage().equals("Expired JWT")) ? "Link expired" : "Jwt verification error"),
          "Jwt error");
    } catch (ParseException | JOSEException e) {
      throw new JwtAuthenticationException(
          Map.of("token", "Помилка при верефікації токену"), "Jwt unknown error");
    }
  }
}

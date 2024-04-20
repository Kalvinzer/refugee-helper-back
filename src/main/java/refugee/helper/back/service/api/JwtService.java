package refugee.helper.back.service.api;

public interface JwtService {

  String createJwt(String email);

  String getEmailFromJwt(String jwt);
}

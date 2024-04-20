package refugee.helper.back.web.exceptions.model;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class DataNotFoundException extends AbstractRefugeeException {

  public DataNotFoundException(Map<String, String> errors, String message) {
    super(HttpStatus.UNAUTHORIZED, errors, message);
  }
}

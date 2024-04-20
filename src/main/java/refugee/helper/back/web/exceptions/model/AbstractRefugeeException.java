package refugee.helper.back.web.exceptions.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class AbstractRefugeeException extends RuntimeException {
  private HttpStatus httpStatus;
  private Map<String, String> errors;
  private String message;
}

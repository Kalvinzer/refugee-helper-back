package refugee.helper.back.web.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import refugee.helper.back.web.dto.response.ApplicationExceptionResponseDto;
import refugee.helper.back.web.exceptions.model.AbstractRefugeeException;
import refugee.helper.back.web.exceptions.model.DataNotFoundException;
import refugee.helper.back.web.exceptions.model.IncorrectCredentialsException;
import refugee.helper.back.web.exceptions.model.JwtAuthenticationException;
import refugee.helper.back.web.exceptions.model.UserNotActivatedException;

@RestControllerAdvice
public class ExceptionHandler {
  private static final Map<Map<String, String>, String> CONSTRAINS_MAP =
      Map.of(Map.of("email", "Емейл вже зареєстровано"), "Ключ \"(email)=");

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApplicationExceptionResponseDto> handleException(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : exception.getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    for (ObjectError objectError : exception.getGlobalErrors()) {
      errors.put(objectError.getObjectName(), objectError.getDefaultMessage());
    }
    return buildResponseDtoAndGetResponseEntity(
        "Помилка валідації введених данних", HttpStatus.UNPROCESSABLE_ENTITY, errors);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApplicationExceptionResponseDto> handleException(
      DataIntegrityViolationException exception) {
    Map<String, String> errors = new HashMap<>();
    Throwable buff = exception.getRootCause();
    if (buff != null) {
      String rootMsg = buff.getMessage();
      if (rootMsg != null) {
        for (Map.Entry<Map<String, String>, String> entry : CONSTRAINS_MAP.entrySet()) {
          if (rootMsg.contains(entry.getValue())) {
            Map.Entry<String, String> errorsMap = entry.getKey().entrySet().iterator().next();
            errors.put(errorsMap.getKey(), errorsMap.getValue());
          }
        }
      }
      if (errors.isEmpty()) {
        errors.put("unknownError", "Невідома помилка, будь-ласка зв'яжіться з адміністрацією");
      }
    }

    return buildResponseDtoAndGetResponseEntity(
        "Data operation error", HttpStatus.CONFLICT, errors);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler({
    IncorrectCredentialsException.class,
    JwtAuthenticationException.class,
    UserNotActivatedException.class,
    DataNotFoundException.class
  })
  public <T extends AbstractRefugeeException>
      ResponseEntity<ApplicationExceptionResponseDto> handleException(T exception) {
    return buildResponseDtoAndGetResponseEntity(
        exception.getMessage(), HttpStatus.UNAUTHORIZED, exception.getErrors());
  }

  private ResponseEntity<ApplicationExceptionResponseDto> buildResponseDtoAndGetResponseEntity(
      String message, HttpStatus status, Map<String, String> errors) {
    return ResponseEntity.status(status)
        .body(
            ApplicationExceptionResponseDto.builder()
                .message(message)
                .status(status.value())
                .errors(errors)
                .build());
  }
}

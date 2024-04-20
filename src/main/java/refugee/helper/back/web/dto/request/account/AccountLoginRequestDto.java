package refugee.helper.back.web.dto.request.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginRequestDto {
  @Email(
      message = "Не валідний емейл",
      regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  @NotEmpty(message = "Емейл не може бути порожнім")
  @Size(max = 32, message = "Емейл не повинен бути більше 32 символів")
  private String email;

  @NotEmpty(message = "Пароль не може бути порожнім")
  private String password;
}

package refugee.helper.back.web.dto.request.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refugee.helper.back.database.entity.VolunteerEntity.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialsRequestDto {
  @Email(
      message = "Не валідний емейл",
      regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  @NotEmpty(message = "Емейл не може бути порожнім")
  @Size(max = 32, message = "Емейл не повинен бути більше 32 символів")
  private String email;

  @Pattern(message = "Не валідний номер телефону", regexp = "^380\\d{9}$")
  @NotEmpty(message = "Телефон не може бути порожнім")
  @Size(max = 12, message = "Телефон не може бути більше 12 символів")
  private String phone;

  @NotEmpty(message = "Ім'я не може бути порожнім")
  @Size(max = 32, message = "Ім'я не повинен бути більше 32 символів")
  private String name;

  @NotEmpty(message = "Прізвище не може бути порожнім")
  @Size(max = 32, message = "Прізвище не повинен бути більше 32 символів")
  private String surname;

  @NotEmpty(message = "c")
  private String password;

  @NotNull(message = "Необхідно вибрати тип допомоги")
  private Type type;
}

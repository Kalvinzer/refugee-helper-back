package refugee.helper.back.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VolunteerAidRequestDto {
  @NotEmpty(message = "Тема не може бути порожньою")
  private String theme;

  @NotEmpty(message = "Контент не може бути порожнім")
  private String content;

  @NotEmpty(message = "Город в якому ви можете надати свої послуги не може бути порожнім")
  private String city;
}

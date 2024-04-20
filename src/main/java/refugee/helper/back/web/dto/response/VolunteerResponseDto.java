package refugee.helper.back.web.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refugee.helper.back.database.entity.VolunteerEntity.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerResponseDto {
  private String email;
  private String name;
  private String surname;
  private String phone;
  private String photo;
  private Type type;
  private LocalDateTime createdOn;
}

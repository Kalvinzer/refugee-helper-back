package refugee.helper.back.service.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refugee.helper.back.database.entity.VolunteerEntity.Type;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VolunteerModel {
  private String id;
  private String email;
  private String name;
  private String surname;
  private String phone;
  private String photo;
  private String password;
  private LocalDateTime createdOn;
  private Boolean enabled;
  private Type type;
}

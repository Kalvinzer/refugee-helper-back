package refugee.helper.back.service.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AidModel {
  private String id;
  private VolunteerModel creator;
  private String theme;
  private String city;
  private String content;
  private LocalDateTime createdOn;
}

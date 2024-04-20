package refugee.helper.back.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class VolunteerAidResponseDto {
  private String id;
  private VolunteerResponseDto creator;
  private String theme;
  private String content;
  private String city;
  private LocalDateTime createdOn;
}

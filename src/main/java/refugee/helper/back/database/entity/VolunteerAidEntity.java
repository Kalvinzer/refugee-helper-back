package refugee.helper.back.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Table(name = "volunteer_aid")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerAidEntity {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "aid_id")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false, insertable = false, updatable = false)
  private VolunteerEntity creator;

  @Column(name = "creator_id")
  private String creatorId;

  @Column(name = "theme")
  private String theme;

  @Column(name = "content")
  private String content;

  @Column(name = "city")
  private String city;

  @Column(name = "created_on")
  private LocalDateTime createdOn;
}

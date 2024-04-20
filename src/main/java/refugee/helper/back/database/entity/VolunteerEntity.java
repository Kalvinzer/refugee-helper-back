package refugee.helper.back.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Table(name = "volunteers")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerEntity {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "volunteer_id")
  private String id;

  @Column(name = "email", length = 32, unique = true)
  private String email;

  @Column(name = "name", length = 32)
  private String name;

  @Column(name = "username", length = 32)
  private String surname;

  @Column(name = "phone", length = 12)
  private String phone;

  @Column(name = "photo")
  private String photo;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
  private List<VolunteerAidEntity> volunteerAid;

  @Column(name = "type")
  @Enumerated(EnumType.ORDINAL)
  private Type type;

  @Column(name = "password")
  private String password;

  @Column(name = "enabled", columnDefinition = "boolean default false")
  private Boolean enabled;

  @Column(name = "created_on")
  private LocalDateTime createdOn;

  public enum Type {
    CARRIER,
    LANDLORD,
    MEDIC,
    PSYCHOLOGIST,
    LAWYER
  }
}

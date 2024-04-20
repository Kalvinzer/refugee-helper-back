package refugee.helper.back.database.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import refugee.helper.back.database.entity.VolunteerEntity;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, String> {
  Optional<VolunteerEntity> getUserEntityByEmail(String email);
}

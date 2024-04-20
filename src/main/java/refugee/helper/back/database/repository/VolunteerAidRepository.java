package refugee.helper.back.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import refugee.helper.back.database.entity.VolunteerAidEntity;
import refugee.helper.back.database.entity.VolunteerEntity.Type;

public interface VolunteerAidRepository extends JpaRepository<VolunteerAidEntity, String> {
  Page<VolunteerAidEntity> findAllByCity(String city, Pageable pageable);

  Page<VolunteerAidEntity> findAllByCreatorType(Type type, Pageable pageable);

  Page<VolunteerAidEntity> findAllByCityAndCreatorType(String city, Type type, Pageable pageable);
}

package refugee.helper.back.service.api;

import java.util.List;
import refugee.helper.back.database.entity.VolunteerEntity.Type;
import refugee.helper.back.service.model.AidModel;

public interface AidService {
  AidModel createVolunteerAid(AidModel aidModel);

  List<AidModel> getFilteredVolunteerAid(String city, Type type, int page, int limit);

  AidModel getVolunteerAidById(String aidId);
}

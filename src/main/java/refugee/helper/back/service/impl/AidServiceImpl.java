package refugee.helper.back.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import refugee.helper.back.database.entity.VolunteerAidEntity;
import refugee.helper.back.database.entity.VolunteerEntity.Type;
import refugee.helper.back.database.repository.VolunteerAidRepository;
import refugee.helper.back.service.api.AidService;
import refugee.helper.back.service.api.UserService;
import refugee.helper.back.service.mappers.ServiceLayerMapper;
import refugee.helper.back.service.model.AidModel;

@Service
@AllArgsConstructor
public class AidServiceImpl implements AidService {
  private final UserService userService;
  private final VolunteerAidRepository volunteerAidRepository;

  @Override
  public AidModel createVolunteerAid(AidModel aidModel) {
    VolunteerAidEntity aidEntity =
        VolunteerAidEntity.builder()
            .creatorId(userService.getAuthenticatedUser().getId())
            .theme(aidModel.getTheme())
            .city(aidModel.getCity())
            .content(aidModel.getContent())
            .createdOn(LocalDateTime.now())
            .build();
    return ServiceLayerMapper.I.aidEntityToModel(volunteerAidRepository.save(aidEntity));
  }

  @Override
  public List<AidModel> getFilteredVolunteerAid(String city, Type type, int page, int limit) {
    Pageable paging = PageRequest.of(page, limit);
    Page<VolunteerAidEntity> volunteerAidEntities =
        city == null
            ? (type == null
                ? volunteerAidRepository.findAll(paging)
                : volunteerAidRepository.findAllByCreatorType(type, paging))
            : (type == null
                ? volunteerAidRepository.findAllByCity(city, paging)
                : volunteerAidRepository.findAllByCityAndCreatorType(city, type, paging));
    return ServiceLayerMapper.I.volunteerAidEntityListToModel(volunteerAidEntities.getContent());
  }

  @Override
  public AidModel getVolunteerAidById(String aidId) {
    return ServiceLayerMapper.I.aidEntityToModel(
        volunteerAidRepository.findById(aidId).orElse(new VolunteerAidEntity()));
  }
}

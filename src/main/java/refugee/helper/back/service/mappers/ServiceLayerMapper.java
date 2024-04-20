package refugee.helper.back.service.mappers;

import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import refugee.helper.back.database.entity.VolunteerAidEntity;
import refugee.helper.back.database.entity.VolunteerEntity;
import refugee.helper.back.service.model.AidModel;
import refugee.helper.back.service.model.SecurityUserModel;
import refugee.helper.back.service.model.VolunteerModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ServiceLayerMapper {
  ServiceLayerMapper I = Mappers.getMapper(ServiceLayerMapper.class);

  SecurityUserModel volunteerEntityToSecurityUserModel(VolunteerEntity userEntity);

  VolunteerModel securityUserModelToModel(SecurityUserModel securityUserModel);

  VolunteerModel volunteerEntityToModel(VolunteerEntity userEntity);

  VolunteerEntity volunteerModelToEntity(VolunteerModel userModel);

  AidModel aidEntityToModel(VolunteerAidEntity volunteerAidEntity);

  List<AidModel> volunteerAidEntityListToModel(List<VolunteerAidEntity> volunteerAidEntityList);
}

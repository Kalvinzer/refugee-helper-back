package refugee.helper.back.web.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import refugee.helper.back.service.model.AidModel;
import refugee.helper.back.service.model.VolunteerModel;
import refugee.helper.back.web.dto.request.VolunteerAidRequestDto;
import refugee.helper.back.web.dto.request.account.AccountCredentialsRequestDto;
import refugee.helper.back.web.dto.request.account.AccountLoginRequestDto;
import refugee.helper.back.web.dto.response.VolunteerAidResponseDto;
import refugee.helper.back.web.dto.response.VolunteerResponseDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface WebLayerMapper {

  WebLayerMapper I = Mappers.getMapper(WebLayerMapper.class);

  VolunteerModel accountCredentialsRequestDtoToVolunteer(AccountCredentialsRequestDto requestDto);

  VolunteerResponseDto volunteerModelToResponseDto(VolunteerModel volunteerModel);

  VolunteerModel accountLoginRequestDtoToVolunteer(AccountLoginRequestDto requestDto);

  VolunteerAidResponseDto volunteerAidModelToResponseDto(AidModel volunteerAid);

  AidModel volunteerAidRequestDtoToModel(VolunteerAidRequestDto requestDto);

  List<VolunteerAidResponseDto> volunteerAidModelListToResponseDto(
      List<AidModel> filteredVolunteerAid);
}

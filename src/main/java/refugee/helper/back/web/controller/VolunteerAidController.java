package refugee.helper.back.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refugee.helper.back.service.api.AidService;
import refugee.helper.back.web.dto.request.VolunteerAidRequestDto;
import refugee.helper.back.web.dto.response.VolunteerAidResponseDto;
import refugee.helper.back.web.mappers.WebLayerMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/a/rest/volunteer/aid")
public class VolunteerAidController {
  private final AidService aidService;

  @PostMapping
  @Operation(security = @SecurityRequirement(name = "Authorization"))
  public ResponseEntity<VolunteerAidResponseDto> createVolunteerAid(
      @RequestBody @Valid VolunteerAidRequestDto requestDto) {
    return ResponseEntity.ok(
        WebLayerMapper.I.volunteerAidModelToResponseDto(
            aidService.createVolunteerAid(
                WebLayerMapper.I.volunteerAidRequestDtoToModel(requestDto))));
  }
}

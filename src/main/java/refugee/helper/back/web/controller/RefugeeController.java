package refugee.helper.back.web.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refugee.helper.back.database.entity.VolunteerEntity.Type;
import refugee.helper.back.service.api.AidService;
import refugee.helper.back.web.dto.response.VolunteerAidResponseDto;
import refugee.helper.back.web.mappers.WebLayerMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/a/rest/refugee")
public class RefugeeController {
  private final AidService aidService;

  @GetMapping("/aid")
  public ResponseEntity<List<VolunteerAidResponseDto>> getFilteredVolunteerAid(
      @RequestParam(required = false) String city,
      @RequestParam(required = false) Type type,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int limit) {
    return ResponseEntity.ok(
        WebLayerMapper.I.volunteerAidModelListToResponseDto(
            aidService.getFilteredVolunteerAid(
                city.equals("null") ? null : city, type, page, limit)));
  }

  @GetMapping("/aid/{aidId}")
  public ResponseEntity<VolunteerAidResponseDto> getAidById(
      @PathVariable(value = "aidId") String aidId) {
    return ResponseEntity.ok(
        WebLayerMapper.I.volunteerAidModelToResponseDto(aidService.getVolunteerAidById(aidId)));
  }
}

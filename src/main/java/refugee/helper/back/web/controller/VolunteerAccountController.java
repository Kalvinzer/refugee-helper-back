package refugee.helper.back.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import refugee.helper.back.service.api.JwtService;
import refugee.helper.back.service.api.UserService;
import refugee.helper.back.service.model.VolunteerModel;
import refugee.helper.back.web.dto.request.account.AccountCredentialsRequestDto;
import refugee.helper.back.web.dto.request.account.AccountLoginRequestDto;
import refugee.helper.back.web.dto.response.AuthorizationTokenResponseDto;
import refugee.helper.back.web.dto.response.VolunteerResponseDto;
import refugee.helper.back.web.mappers.WebLayerMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/a/rest/volunteer/account")
public class VolunteerAccountController {
  private final UserService userService;
  private final JwtService jwtService;

  @PostMapping(
      path = "/register",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<VolunteerResponseDto> registerAccount(
      @RequestPart(name = "file") MultipartFile photo,
      @ModelAttribute @ParameterObject @Valid AccountCredentialsRequestDto requestDto) {
    VolunteerModel volunteerModel =
        userService.registerUser(
            WebLayerMapper.I.accountCredentialsRequestDtoToVolunteer(requestDto), photo);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(WebLayerMapper.I.volunteerModelToResponseDto(volunteerModel));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthorizationTokenResponseDto> loginInAccount(
      @RequestBody @Valid AccountLoginRequestDto requestDto) {
    userService.isCorrectPassword(WebLayerMapper.I.accountLoginRequestDtoToVolunteer(requestDto));

    return ResponseEntity.ok(
        AuthorizationTokenResponseDto.builder()
            .authorizationToken(jwtService.createJwt(requestDto.getEmail()))
            .build());
  }

  @GetMapping
  @Operation(security = @SecurityRequirement(name = "Authorization"))
  public ResponseEntity<VolunteerResponseDto> getUser() {
    return ResponseEntity.ok(
        WebLayerMapper.I.volunteerModelToResponseDto(userService.getAuthenticatedUser()));
  }
}

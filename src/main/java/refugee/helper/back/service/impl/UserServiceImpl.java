package refugee.helper.back.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import refugee.helper.back.database.repository.VolunteerRepository;
import refugee.helper.back.service.api.PictureService;
import refugee.helper.back.service.api.UserService;
import refugee.helper.back.service.mappers.ServiceLayerMapper;
import refugee.helper.back.service.model.SecurityUserModel;
import refugee.helper.back.service.model.VolunteerModel;
import refugee.helper.back.web.exceptions.model.IncorrectCredentialsException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final PictureService pictureService;
  private final VolunteerRepository volunteerRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public SecurityUserModel getUserByEmail(String email) {
    return ServiceLayerMapper.I.volunteerEntityToSecurityUserModel(
        volunteerRepository
            .getUserEntityByEmail(email)
            .orElseThrow(
                () ->
                    new IncorrectCredentialsException(
                        Map.of("email", String.format("Юзер з емейлом '%s', не знайдений", email)),
                        "Incorrect email")));
  }

  @Override
  public VolunteerModel registerUser(VolunteerModel volunteerModel, MultipartFile picture) {
    volunteerModel.setPhoto(pictureService.savePicture(picture, volunteerModel.getEmail()));
    volunteerModel.setPassword(passwordEncoder.encode(volunteerModel.getPassword()));
    volunteerModel.setCreatedOn(LocalDateTime.now());
    volunteerModel.setEnabled(false);

    return ServiceLayerMapper.I.volunteerEntityToModel(
        Optional.of(
                volunteerRepository.save(
                    ServiceLayerMapper.I.volunteerModelToEntity(volunteerModel)))
            .orElseThrow(() -> new RuntimeException("Помилка при збереженні юзера")));
  }

  public boolean isCorrectPassword(VolunteerModel volunteerModel) {
    if (!passwordEncoder.matches(
        volunteerModel.getPassword(), getUserByEmail(volunteerModel.getEmail()).getPassword())) {
      throw new IncorrectCredentialsException(
          Map.of("password", "Будь-ласка введіть коректний пароль"), "Incorrect password");
    }
    return true;
  }

  @Override
  public VolunteerModel getAuthenticatedUser() {
    return ServiceLayerMapper.I.securityUserModelToModel(
        (SecurityUserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
  }
}

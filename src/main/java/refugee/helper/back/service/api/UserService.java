package refugee.helper.back.service.api;

import org.springframework.web.multipart.MultipartFile;
import refugee.helper.back.service.model.SecurityUserModel;
import refugee.helper.back.service.model.VolunteerModel;

public interface UserService {

  SecurityUserModel getUserByEmail(String email);

  VolunteerModel registerUser(VolunteerModel userModel, MultipartFile picture);

  boolean isCorrectPassword(VolunteerModel userModel);

  VolunteerModel getAuthenticatedUser();
}

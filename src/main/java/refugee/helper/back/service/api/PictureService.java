package refugee.helper.back.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
  String savePicture(MultipartFile picture, String fileName);
}

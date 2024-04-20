package refugee.helper.back.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import refugee.helper.back.service.api.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
  private final String IMAGE_PATH =
      getClass().getClassLoader().getResource("static/images/").toString();

  @Override
  public String savePicture(MultipartFile picture, String fileName) {
    String filePath = Paths.get(IMAGE_PATH.substring(6)) + "\\" + fileName + ".png";
    File file = new File(filePath);

    try (OutputStream os = new FileOutputStream(file)) {
      os.write(picture.getBytes());
    } catch (Exception ex) {
      throw new RuntimeException("Помилка при збереженні картинки");
    }
    return "http://localhost:8080/images/" + fileName + ".png";
  }
}

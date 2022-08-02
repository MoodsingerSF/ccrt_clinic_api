package com.moodsinger.ccrt_clinic.shared;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {
  public String saveFile(String uploadDir, String fileName,
      MultipartFile multipartFile) throws IOException {
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    InputStream inputStream = multipartFile.getInputStream();
    Path filePath = uploadPath.resolve(fileName);
    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    return filePath.toString();
  }
}

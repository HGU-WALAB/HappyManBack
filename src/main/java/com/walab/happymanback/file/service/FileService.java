package com.walab.happymanback.file.service;

import com.walab.happymanback.file.dto.FileDto;
import com.walab.happymanback.file.exception.FileUploadFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {
  @Value("${custom.file.path}")
  private String FILE_PATH;

  public FileDto uploadFile(MultipartFile file, String dir) {
    String originFileName = file.getOriginalFilename();
    String storedFileName = getUniqueFileName(originFileName);
    String filePath = FILE_PATH + dir + "/";
    File directory = new File(filePath);

    if (!directory.exists()) {
      try {
        Files.createDirectories(Paths.get(filePath));
      } catch (IOException e) {
        throw new FileUploadFailException("Failed to create directory: " + e.getMessage());
      }
    }

    try {
      File storedFile = new File(filePath + storedFileName);
      file.transferTo(storedFile);
    } catch (IOException e) {
      throw new FileUploadFailException(e.getMessage());
    }

    return FileDto.builder()
            .originFileName(originFileName)
            .storedFileName(storedFileName)
            .build();
  }

  public List<FileDto> uploadFiles(List<MultipartFile> files, String dir) {
    return files.stream()
            .map(f -> uploadFile(f, dir))
            .collect(Collectors.toList());
  }

  private static String getUniqueFileName(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    String name;
    String extension = "";
    if (dotIndex != -1) {
      name = fileName.substring(0, dotIndex);
      extension = fileName.substring(dotIndex); // 확장자 유지
    } else {
      name = fileName;
    }
    return name + "_" + UUID.randomUUID() + extension;
  }
}

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
import java.nio.file.attribute.PosixFilePermission;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {
  @Value("${custom.file.path}")
  private String FILE_PATH;

  @Value("${custom.file.pattern}")
  private String FILE_PATTERN;

  public FileDto uploadOneFile(MultipartFile file, String filePath){
    if (file==null || file.isEmpty()) {
      return null;
    }
    return uploadFile(file, makeDirectory(filePath));
  }

  public FileDto uploadFile(MultipartFile file, String filePath) {
    String originFileName = file.getOriginalFilename();
    String storedFileName = getUniqueFileName(originFileName);
    String absolutePath = FILE_PATH + filePath;
    try {
      File storedFile = new File(absolutePath + storedFileName);
      file.transferTo(storedFile);
      storedFile.setReadable(true, false);
    } catch (IOException e) {
      throw new FileUploadFailException(e.getMessage());
    }

    return FileDto.builder()
            .originFileName(originFileName)
            .storedFilePath(filePath + storedFileName)
            .build();
  }

  // 디렉토리 권한을 775로 설정
  private void setDirectoryPermissions(String directoryPath) throws IOException {
    Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    perms.add(PosixFilePermission.OWNER_EXECUTE);
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    perms.add(PosixFilePermission.GROUP_EXECUTE);
    perms.add(PosixFilePermission.OTHERS_READ);
    Files.setPosixFilePermissions(Paths.get(directoryPath), perms);
  }

  public List<FileDto> uploadFiles(List<MultipartFile> files, String filePath) {
    if (files == null || files.isEmpty()) {
      return new ArrayList<>();
    }
    return files.stream()
            .map(f -> uploadOneFile(f, filePath))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }

  private synchronized String makeDirectory(String filePath) {
    filePath = filePath + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/";
    String absolutePath = FILE_PATH + filePath;
    File directory = new File(absolutePath);
    if (!directory.exists()) {
      try {
        Files.createDirectories(Paths.get(absolutePath));
        while (!(directory.getAbsolutePath()+"/").equals(FILE_PATH)) {
          setDirectoryPermissions(directory.getAbsolutePath());
          if (directory.getParentFile() != null) {
            directory = directory.getParentFile();
          } else {
            break;
          }
        }
        return filePath;
      } catch (IOException e) {
        throw new FileUploadFailException("Failed to create directory: " + e.getMessage());
      }
    }
    return filePath;
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

  public String getFile(String filePath) {
    if (filePath == null) {
      return null;
    }
    return FILE_PATTERN + "/" +filePath;
  }
}
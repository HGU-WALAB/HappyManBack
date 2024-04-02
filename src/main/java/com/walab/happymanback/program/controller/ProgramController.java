package com.walab.happymanback.program.controller;

import com.walab.happymanback.file.service.FileService;
import com.walab.happymanback.program.controller.request.AddProgramRequest;
import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProgramController {
  private final ProgramService programService;

  private final FileService fileService;

  private static final String PROGRAM_IMAGE_DIR = "program/image";

  private static final String PROGRAM_FILE_DIR = "program/file";

  @PostMapping("/api/happyman/admin/program")
  public ResponseEntity<Void> createProgram(
      @ModelAttribute AddProgramRequest request,
      @RequestParam(value = "image", required = false) MultipartFile image,
      @RequestParam(value = "file", required = false) List<MultipartFile> file) {
    programService.createProgram(
        ProgramDto.from(
            request,
            fileService.uploadFile(image, PROGRAM_IMAGE_DIR),
            fileService.uploadFiles(file, PROGRAM_FILE_DIR)));
    return ResponseEntity.ok().build();
  }
}

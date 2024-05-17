package com.walab.happymanback.program.controller;

import com.walab.happymanback.bookmark.service.BookmarkService;
import com.walab.happymanback.file.service.FileService;
import com.walab.happymanback.participant.dto.ParticipantDto;
import com.walab.happymanback.participant.service.ParticipantService;
import com.walab.happymanback.program.controller.request.AddProgramRequest;
import com.walab.happymanback.program.controller.request.ProgramApplyRequest;
import com.walab.happymanback.program.controller.request.UpdateProgramRequest;
import com.walab.happymanback.program.controller.response.*;
import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProgramController {
  private final ProgramService programService;

  private final FileService fileService;

  private final BookmarkService bookmarkService;

  private final ParticipantService participantService;
  private static final String PROGRAM_IMAGE_DIR = "program/image/";

  private static final String PROGRAM_FILE_DIR = "program/file/";

  @PostMapping("/api/happyman/admin/programs")
  public ResponseEntity<Void> createProgram(
      @ModelAttribute AddProgramRequest request,
      @RequestParam(value = "image", required = false) MultipartFile image,
      @RequestParam(value = "file", required = false) List<MultipartFile> file) {
    programService.createProgram(
        ProgramDto.from(
            request,
            fileService.uploadOneFile(image, PROGRAM_IMAGE_DIR),
            fileService.uploadFiles(file, PROGRAM_FILE_DIR)));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/happyman/programs")
  public ResponseEntity<ProgramListResponse> getPrograms(@AuthenticationPrincipal String uniqueId) {
    List<ProgramDto> programs = programService.getProgramsWithCategory();
    programs.forEach(program -> program.setImage(fileService.getFile(program.getImage())));
    return ResponseEntity.ok(
        ProgramListResponse.from(programs, bookmarkService.getBookmarks(uniqueId)));
  }

  @GetMapping("/api/happyman/all/programs")
  public ResponseEntity<NotLoginProgramListResponse> getPrograms() {
    List<ProgramDto> programs = programService.getProgramsWithCategory();
    programs.forEach(program -> program.setImage(fileService.getFile(program.getImage())));
    return ResponseEntity.ok(NotLoginProgramListResponse.from(programs));
  }

  @GetMapping("/api/happyman/programs/{id}")
  public ResponseEntity<ProgramDetailResponse> getProgram(
      @AuthenticationPrincipal String uniqueId, @PathVariable Long id) {
    ProgramDto program = programService.getProgramWithFile(id);
    program
        .getProgramFileDtos()
        .forEach(
            programFileDto ->
                programFileDto.setStoredFilePath(
                    fileService.getFile(programFileDto.getStoredFilePath())));
    program.setImage(fileService.getFile(program.getImage()));
    return ResponseEntity.ok(
        ProgramDetailResponse.from(
            program,
            bookmarkService.isBookmarked(uniqueId, id),
            participantService.isParticipant(id, uniqueId)));
  }

  @GetMapping("/api/happyman/admin/programs")
  public ResponseEntity<AdminProgramListResponse> adminGetPrograms() {
    List<ProgramDto> programs = programService.getProgramsWithCategory();
    programs.forEach(program -> program.setImage(fileService.getFile(program.getImage())));
    return ResponseEntity.ok(AdminProgramListResponse.from(programs));
  }

  @GetMapping("/api/happyman/admin/programs/{id}")
  public ResponseEntity<AdminProgramDetailResponse> adminGetProgram(@PathVariable Long id) {
    ProgramDto program = programService.getProgramWithFile(id);
    program
        .getProgramFileDtos()
        .forEach(
            programFileDto ->
                programFileDto.setStoredFilePath(
                    fileService.getFile(programFileDto.getStoredFilePath())));
    program.setImage(fileService.getFile(program.getImage()));
    return ResponseEntity.ok(AdminProgramDetailResponse.from(program));
  }

  @PatchMapping("/api/happyman/admin/programs/{id}")
  public ResponseEntity<Void> updateProgram(
      @PathVariable Long id,
      @ModelAttribute UpdateProgramRequest request,
      @RequestParam(value = "image", required = false) MultipartFile image,
      @RequestParam(value = "file", required = false) List<MultipartFile> file) {
    programService.updateProgram(
        id,
        ProgramDto.from(
            request,
            fileService.uploadOneFile(image, PROGRAM_IMAGE_DIR),
            fileService.uploadFiles(file, PROGRAM_FILE_DIR)));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/happyman/programs/{id}/application")
  public ResponseEntity<ApplicationResponse> getApplicationForm(@PathVariable Long id) {
    ProgramDto program = programService.getProgramWithCategory(id);
    program.setImage(fileService.getFile(program.getImage()));
    return ResponseEntity.ok(ApplicationResponse.from(program));
  }

  @PostMapping("/api/happyman/programs/{id}/apply")
  public ResponseEntity<Void> applyProgram(
      @RequestBody ProgramApplyRequest request,
      @PathVariable Long id,
      @AuthenticationPrincipal String uniqueId) {
    participantService.applyProgram(ParticipantDto.from(request, uniqueId, id));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/happyman/admin/programs/{id}/applicants")
  public ResponseEntity<ApplicantListResponse> getApplicants(@PathVariable Long id) {
    return ResponseEntity.ok(
        ApplicantListResponse.from(programService.getProgramWithParticipant(id)));
  }
}

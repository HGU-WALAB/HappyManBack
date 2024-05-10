package com.walab.happymanback.program.dto;

import com.walab.happymanback.category.dto.CategoryDto;
import com.walab.happymanback.category.entity.Category;
import com.walab.happymanback.file.dto.FileDto;
import com.walab.happymanback.program.controller.request.AddProgramRequest;
import com.walab.happymanback.program.controller.request.UpdateProgramRequest;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.program.entity.ProgramFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Setter
public class ProgramDto {
  private Long id;

  private String name;

  private Integer quota;

  private Integer currentQuota;

  private String information;

  private LocalDateTime applyStartDate;

  private LocalDateTime applyEndDate;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String applicationForm;

  private String surveyForm;

  private String managerName;

  private String managerContact;

  private String image;

  private CategoryDto categoryDto;

  private List<ProgramFileDto> programFileDtos;

  public static ProgramDto from(
      AddProgramRequest request, FileDto imageDto, List<FileDto> fileDtos) {
    ProgramDto dto =
        ProgramDto.builder()
            .name(request.getName())
            .quota(request.getQuota())
            .information(request.getInformation())
            .applyStartDate(request.getApplyStartDate())
            .applyEndDate(request.getApplyEndDate())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .applicationForm(request.getApplicationForm())
            .managerName(request.getManagerName())
            .managerContact(request.getManagerContact())
            .categoryDto(CategoryDto.builder().id(request.getCategoryId()).build())
            .programFileDtos(
                fileDtos.stream().map(ProgramFileDto::from).collect(Collectors.toList()))
            .build();
    if (imageDto != null) {
      dto.setImage(imageDto.getStoredFilePath());
    }
    return dto;
  }

  public static ProgramDto from(
      UpdateProgramRequest request, FileDto imageDto, List<FileDto> fileDtos) {
    ProgramDto dto =
        ProgramDto.builder()
            .name(request.getName())
            .quota(request.getQuota())
            .information(request.getInformation())
            .applyStartDate(request.getApplyStartDate())
            .applyEndDate(request.getApplyEndDate())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .managerName(request.getManagerName())
            .managerContact(request.getManagerContact())
            .programFileDtos(
                fileDtos.stream().map(ProgramFileDto::from).collect(Collectors.toList()))
            .build();
    if (imageDto != null) {
      dto.setImage(imageDto.getStoredFilePath());
    }
    return dto;
  }

  public static ProgramDto from(Program program) {
    return ProgramDto.builder()
        .id(program.getId())
        .name(program.getName())
        .quota(program.getQuota())
        .currentQuota(program.getCurrentQuota())
        .information(program.getInformation())
        .applyStartDate(program.getApplyStartDate())
        .applyEndDate(program.getApplyEndDate())
        .startDate(program.getStartDate())
        .endDate(program.getEndDate())
        .applicationForm(program.getApplicationForm())
        .managerName(program.getManagerName())
        .managerContact(program.getManagerContact())
        .image(program.getImage())
        .build();
  }

  public static ProgramDto from(Program program, Category category) {
    return ProgramDto.builder()
        .id(program.getId())
        .name(program.getName())
        .quota(program.getQuota())
        .currentQuota(program.getCurrentQuota())
        .information(program.getInformation())
        .applyStartDate(program.getApplyStartDate())
        .applyEndDate(program.getApplyEndDate())
        .startDate(program.getStartDate())
        .endDate(program.getEndDate())
        .applicationForm(program.getApplicationForm())
        .managerName(program.getManagerName())
        .managerContact(program.getManagerContact())
        .image(program.getImage())
        .categoryDto(CategoryDto.from(category))
        .build();
  }

  public static ProgramDto from(Program program, List<ProgramFile> programFiles) {
    return ProgramDto.builder()
        .id(program.getId())
        .name(program.getName())
        .quota(program.getQuota())
        .currentQuota(program.getCurrentQuota())
        .information(program.getInformation())
        .applyStartDate(program.getApplyStartDate())
        .applyEndDate(program.getApplyEndDate())
        .startDate(program.getStartDate())
        .endDate(program.getEndDate())
        .applicationForm(program.getApplicationForm())
        .managerName(program.getManagerName())
        .managerContact(program.getManagerContact())
        .image(program.getImage())
        .programFileDtos(
            programFiles.stream().map(ProgramFileDto::from).collect(Collectors.toList()))
        .build();
  }
}

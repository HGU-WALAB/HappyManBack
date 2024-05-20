package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.program.dto.ProgramFileDto;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class AdminProgramDetailResponse {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private Long id;

  private String name;

  private Integer quota;

  private Integer currentQuota;

  private String information;

  private String applyStartDate;

  private String applyEndDate;

  private String startDate;

  private String endDate;

  private String managerName;

  private String managerContact;

  private String image;

  private Long categoryId;

  private List<ProgramFile> programFiles;
  public static AdminProgramDetailResponse from(
      ProgramDto programDto) {
    return AdminProgramDetailResponse.builder()
        .id(programDto.getId())
        .name(programDto.getName())
        .quota(programDto.getQuota())
        .currentQuota(programDto.getCurrentQuota())
        .information(programDto.getInformation())
        .applyStartDate(programDto.getApplyStartDate().format(DATE_TIME_FORMATTER))
        .applyEndDate(programDto.getApplyEndDate().format(DATE_TIME_FORMATTER))
        .startDate(programDto.getStartDate().format(DATE_TIME_FORMATTER))
        .endDate(programDto.getEndDate().format(DATE_TIME_FORMATTER))
        .managerName(programDto.getManagerName())
        .managerContact(programDto.getManagerContact())
        .image(programDto.getImage())
        .categoryId(programDto.getCategoryDto().getId())
        .programFiles(
            programDto.getProgramFileDtos().stream()
                .map(ProgramFile::from)
                .collect(Collectors.toList()))
        .build();
  }

  @Getter
  @Builder
  private static class ProgramFile {
    private String originFileName;
    private String storedFilePath;

    public static ProgramFile from(ProgramFileDto dto) {
      return ProgramFile.builder()
          .originFileName(dto.getOriginFileName())
          .storedFilePath(dto.getStoredFilePath())
          .build();
    }
  }
}

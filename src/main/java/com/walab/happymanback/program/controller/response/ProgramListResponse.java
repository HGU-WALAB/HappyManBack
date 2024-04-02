package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.program.controller.response.enums.ProgramStatus;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ProgramListResponse {
  private List<Program> programs;

  public static ProgramListResponse from(List<ProgramDto> dtos) {
    ProgramListResponse response = new ProgramListResponse();
    response.programs = dtos.stream().map(Program::from).collect(Collectors.toList());
    return response;
  }

  @NoArgsConstructor
  @Setter
  @Getter
  private static class Program {
    private Long id;
    private Long categoryId;
    private String name;
    private String status;
    private String image;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyEndDate;

    private static Program from(ProgramDto dto) {
      Program program = new Program();
      program.id = dto.getId();
      program.categoryId = dto.getCategoryDto().getId();
      program.name = dto.getName();
      program.image = dto.getImage();
      program.applyEndDate = dto.getApplyEndDate();
      program.status = evaluateStatus(dto.getApplyStartDate(), dto.getApplyEndDate()).getKorean();
      return program;
    }

    private static ProgramStatus evaluateStatus(
        LocalDateTime applyStartDate, LocalDateTime applyEndDate) {
      LocalDateTime now = LocalDateTime.now();
      if (now.isBefore(applyStartDate)) {
        return ProgramStatus.BEFORE_RECRUITMENT;
      } else if (now.isAfter(applyStartDate) && now.isBefore(applyEndDate)) {
        return ProgramStatus.RECRUITING;
      } else {
        return ProgramStatus.RECRUITMENT_END;
      }
    }
  }
}

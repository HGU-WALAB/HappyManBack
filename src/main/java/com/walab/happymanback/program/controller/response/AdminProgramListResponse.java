package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.program.controller.response.enums.AdminProgramStatus;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class AdminProgramListResponse {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private List<Program> programs;

    public static AdminProgramListResponse from(List<ProgramDto> programDtos) {
        return AdminProgramListResponse.builder()
                .programs(
                        programDtos.stream()
                                .map(Program::from)
                                .collect(Collectors.toList()))
                .build();
    }
    @NoArgsConstructor
    @Setter
    @Getter
    private static class Program {
        private Long id;
        private String name;
        private String category;
        private Long categoryId;
        private String ApplyStartDate;
        private String ApplyEndDate;
        private String startDate;
        private String endDate;
        private String managerName;
        private Integer quota;
        private Integer currentQuota;
        private String status;

        private static Program from(ProgramDto programDto) {
            Program program = new Program();
            program.id = programDto.getId();
            program.name = programDto.getName();
            program.category = programDto.getCategoryDto().getName();
            program.categoryId = programDto.getCategoryDto().getId();
            program.ApplyStartDate = programDto.getApplyStartDate().format(DATE_TIME_FORMATTER);
            program.ApplyEndDate = programDto.getApplyEndDate().format(DATE_TIME_FORMATTER);
            program.startDate = programDto.getStartDate().format(DATE_TIME_FORMATTER);
            program.endDate = programDto.getEndDate().format(DATE_TIME_FORMATTER);
            program.managerName = programDto.getManagerName();
            program.quota = programDto.getQuota();
            program.currentQuota = programDto.getCurrentQuota();
            program.status = evaluateStatus(programDto.getApplyStartDate(), programDto.getApplyEndDate()).getKorean();
            return program;
        }

        private static AdminProgramStatus evaluateStatus(LocalDateTime startDate, LocalDateTime endDate) {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(startDate)) {
                return AdminProgramStatus.WAITING;
            } else if (now.isAfter(startDate) && now.isBefore(endDate)) {
                return AdminProgramStatus.RECRUITING;
            } else {
                return AdminProgramStatus.END;
            }
        }
    }
}

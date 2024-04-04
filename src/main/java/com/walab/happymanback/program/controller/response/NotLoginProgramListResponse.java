package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.program.controller.response.enums.ProgramStatus;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class NotLoginProgramListResponse {
    private List<Program> programs;

    public static NotLoginProgramListResponse from(List<ProgramDto> programDtos) {
        return NotLoginProgramListResponse.builder()
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
        private Long categoryId;
        private String name;
        private String status;
        private String image;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime applyEndDate;

        private static Program from(ProgramDto programDto) {
            Program program = new Program();
            program.id = programDto.getId();
            program.categoryId = programDto.getCategoryDto().getId();
            program.name = programDto.getName();
            program.image = programDto.getImage();
            program.applyEndDate = programDto.getApplyEndDate();
            program.status = evaluateStatus(programDto.getApplyStartDate(), programDto.getApplyEndDate()).getKorean();
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

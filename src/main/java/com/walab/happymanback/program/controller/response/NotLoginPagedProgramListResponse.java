package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class NotLoginPagedProgramListResponse {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private List<Program> programs;
    private Integer totalPage;

    public static NotLoginPagedProgramListResponse from(List<ProgramDto> programDtos, Integer totalPage) {
        return NotLoginPagedProgramListResponse.builder()
                .programs(
                        programDtos.stream()
                                .map(Program::from)
                                .collect(Collectors.toList()))
                .totalPage(totalPage)
                .build();
    }
    @NoArgsConstructor
    @Setter
    @Getter
    private static class Program {
        private Long id;
        private String name;
        private String status;
        private String image;
        private String teacher;
        private String applyEndDate;

        private static Program from(ProgramDto programDto) {
            Program program = new Program();
            program.id = programDto.getId();
            program.name = programDto.getName();
            program.image = programDto.getImage();
            program.teacher = programDto.getTeacher();
            program.applyEndDate = programDto.getApplyEndDate().format(DATE_TIME_FORMATTER);
            return program;
        }
    }
}
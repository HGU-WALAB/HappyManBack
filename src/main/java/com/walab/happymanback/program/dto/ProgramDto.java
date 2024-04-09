package com.walab.happymanback.program.dto;

import com.walab.happymanback.category.dto.CategoryDto;
import com.walab.happymanback.file.dto.FileDto;
import com.walab.happymanback.program.controller.request.AddProgramRequest;
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

    public static ProgramDto from(AddProgramRequest request, FileDto imageDto, List<FileDto> fileDtos) {
        ProgramDto dto=ProgramDto.builder()
                .name(request.getName())
                .quota(request.getQuota())
                .currentQuota(request.getCurrentQuota())
                .information(request.getInformation())
                .applyStartDate(request.getApplyStartDate())
                .applyEndDate(request.getApplyEndDate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .applicationForm(request.getApplicationForm())
                .surveyForm(request.getSurveyForm())
                .managerName(request.getManagerName())
                .managerContact(request.getManagerContact())
                .categoryDto(CategoryDto.builder().id(request.getCategoryId()).build())
                .programFileDtos(fileDtos.stream().map(ProgramFileDto::from).collect(Collectors.toList()))
                .build();
        if(imageDto!=null){
            dto.setImage(imageDto.getStoredFilePath());
        }
        return dto;
    }
}

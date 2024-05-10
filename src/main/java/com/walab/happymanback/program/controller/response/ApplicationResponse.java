package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ApplicationResponse {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long id;

    private String name;

    private Integer quota;

    private Integer currentQuota;

    private String applyStartDate;

    private String applyEndDate;

    private String startDate;

    private String endDate;

    private String managerName;

    private String managerContact;

    private String applicationForm;

    private String image;

    private String category;

    public static ApplicationResponse from(ProgramDto programDto) {
        return ApplicationResponse.builder()
                .id(programDto.getId())
                .name(programDto.getName())
                .quota(programDto.getQuota())
                .currentQuota(programDto.getCurrentQuota())
                .applyStartDate(programDto.getApplyStartDate().format(DATE_TIME_FORMATTER))
                .applyEndDate(programDto.getApplyEndDate().format(DATE_TIME_FORMATTER))
                .startDate(programDto.getStartDate().format(DATE_TIME_FORMATTER))
                .endDate(programDto.getEndDate().format(DATE_TIME_FORMATTER))
                .managerName(programDto.getManagerName())
                .managerContact(programDto.getManagerContact())
                .applicationForm(programDto.getApplicationForm())
                .image(programDto.getImage())
                .category(programDto.getCategoryDto().getName())
                .build();
    }
}

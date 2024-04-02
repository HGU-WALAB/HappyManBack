package com.walab.happymanback.program.controller.response.enums;

import lombok.Getter;

@Getter
public enum ProgramStatus {
    RECRUITING("신청 진행"),
    BEFORE_RECRUITMENT("신청 대기"),
    RECRUITMENT_END("신청 마감");

    private final String korean;

    ProgramStatus(String korean) {
        this.korean = korean;
    }
}

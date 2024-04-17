package com.walab.happymanback.program.controller.response.enums;

import lombok.Getter;

@Getter
public enum AdminProgramStatus {
    WAITING("대기"),
    RECRUITING("진행"),
    END("마감");

    private final String korean;

    AdminProgramStatus(String korean) {
        this.korean = korean;
    }
}

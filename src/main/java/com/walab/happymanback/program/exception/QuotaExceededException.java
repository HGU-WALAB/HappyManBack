package com.walab.happymanback.program.exception;

public class QuotaExceededException extends RuntimeException{
    private static final String MESSAGE = "정원이 초과되었습니다.";

    public QuotaExceededException() {
        super(MESSAGE);
    }
}

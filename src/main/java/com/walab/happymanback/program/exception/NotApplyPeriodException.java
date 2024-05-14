package com.walab.happymanback.program.exception;

public class NotApplyPeriodException extends RuntimeException {
  private static final String MESSAGE = "신청 기간이 아닙니다.";

  public NotApplyPeriodException() {
    super(MESSAGE);
  }
}

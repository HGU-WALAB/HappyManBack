package com.walab.happymanback.participant.exception;

public class AlreadyAppliedException extends RuntimeException {
  private static final String MESSAGE = "이미 신청한 프로그램입니다.";

  public AlreadyAppliedException() {
    super(MESSAGE);
  }
}

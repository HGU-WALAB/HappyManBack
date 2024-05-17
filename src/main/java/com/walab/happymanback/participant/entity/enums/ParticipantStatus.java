package com.walab.happymanback.participant.entity.enums;

import com.walab.happymanback.base.exception.DoNotExistException;
import lombok.Getter;

@Getter
public enum ParticipantStatus {
  WAITING("대기"),
  REJECTED("거절"),
  APPROVED("미수료"),
  COMPLETED("수료");

  private final String korean;

  ParticipantStatus(String korean) {
    this.korean = korean;
  }

  public static ParticipantStatus from(String status) {
    for (ParticipantStatus participantStatus : ParticipantStatus.values()) {
      if (participantStatus.name().equals(status)) {
        return participantStatus;
      }
    }
    throw new DoNotExistException("해당하는 상태가 없습니다.");
  }
}

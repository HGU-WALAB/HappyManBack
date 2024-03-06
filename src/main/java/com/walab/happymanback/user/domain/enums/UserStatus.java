package com.walab.happymanback.user.domain.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
  USER("유저"),
  ADMIN("관리자");

  private final String korean;

  UserStatus(String korean) {
    this.korean = korean;
  }

  public static UserStatus from(String korean) {
    for (UserStatus userStatus : UserStatus.values()) {
      if (userStatus.getKorean().equals(korean)) {
        return userStatus;
      }
    }
    throw new IllegalArgumentException("유저 상태가 존재하지 않습니다.");
  }
}

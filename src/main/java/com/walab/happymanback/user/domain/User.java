package com.walab.happymanback.user.domain;

import com.walab.happymanback.base.domain.BaseTime;
import com.walab.happymanback.user.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {
  @Id
  @Column(nullable = false, length = 50)
  private String uniqueId;

  @Column(nullable = false, length = 50)
  private String name;

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @Column(length = 320)
  private String email;

  private Integer grade;

  private Integer semester;

  private String department;

  private String major1;

  private String major2;

  public static User from(String uniqueId) {
    return User.builder()
        .uniqueId(uniqueId)
        .name("홍길동")
        .email(uniqueId + "@handong.ac.kr")
        .status(UserStatus.USER)
        .grade(3)
        .semester(1)
        .department("전산전자공학부")
        .major1("컴퓨터공학 심화")
        .major2("컴퓨터공학 심화")
        .build();
  }
}

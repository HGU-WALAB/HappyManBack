package com.walab.happymanback.user.domain;

import com.walab.happymanback.auth.dto.AuthDto;
import com.walab.happymanback.base.domain.BaseTime;
import com.walab.happymanback.user.domain.enums.UserStatus;
import javax.persistence.*;
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
  public void update(AuthDto dto) {
    this.name = dto.getName();
    this.email = dto.getEmail();
    this.grade = dto.getGrade();
    this.semester = dto.getSemester();
    this.department = dto.getDepartment();
    this.major1 = dto.getMajor1();
    this.major2 = dto.getMajor2();
  }

  public static User from(AuthDto dto) {
    return User.builder()
        .uniqueId(dto.getUniqueId())
        .name(dto.getName())
        .email(dto.getEmail())
        .status(UserStatus.USER)
        .grade(dto.getGrade())
        .semester(dto.getSemester())
        .department(dto.getDepartment())
        .major1(dto.getMajor1())
        .major2(dto.getMajor2())
        .build();
  }
}

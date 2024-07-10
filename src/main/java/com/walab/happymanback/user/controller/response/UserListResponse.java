package com.walab.happymanback.user.controller.response;

import com.walab.happymanback.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class UserListResponse {
  private List<User> users;

  public static UserListResponse from(List<UserDto> users) {
    return UserListResponse.builder()
        .users(users.stream().map(User::from).collect(Collectors.toList()))
        .build();
  }

  @Getter
  @Builder
  private static class User {
    private String uniqueId;
    private String name;
    private String status;
    private String email;
    private Integer grade;
    private Integer semester;
    private String department;
    private String major1;
    private String major2;

    public static User from(UserDto user) {
      return User.builder()
          .uniqueId(user.getUniqueId())
          .name(user.getName())
          .status(user.getStatus())
          .email(user.getEmail())
          .grade(user.getGrade())
          .semester(user.getSemester())
          .department(user.getDepartment())
          .major1(user.getMajor1())
          .major2(user.getMajor2())
          .build();
    }
  }
}

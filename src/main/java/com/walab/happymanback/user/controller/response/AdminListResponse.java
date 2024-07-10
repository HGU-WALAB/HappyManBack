package com.walab.happymanback.user.controller.response;

import com.walab.happymanback.user.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminListResponse {
  private List<User> users;

  public static AdminListResponse from(List<UserDto> users) {
    return AdminListResponse.builder()
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
    private String department;

    public static User from(UserDto user) {
      return User.builder()
          .uniqueId(user.getUniqueId())
          .name(user.getName())
          .status(user.getStatus())
          .email(user.getEmail())
          .department(user.getDepartment())
          .build();
    }
  }
}

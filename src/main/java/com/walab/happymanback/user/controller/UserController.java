package com.walab.happymanback.user.controller;

import com.walab.happymanback.user.controller.response.AdminListResponse;
import com.walab.happymanback.user.controller.response.MyProfileResponse;
import com.walab.happymanback.user.controller.response.UserListResponse;
import com.walab.happymanback.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/api/happyman/my-profile")
  public ResponseEntity<MyProfileResponse> getMyProfile(@AuthenticationPrincipal String uniqueId) {
    return ResponseEntity.ok(MyProfileResponse.from(userService.getUser(uniqueId)));
  }

  @GetMapping("/api/happyman/admin/users/user")
  public ResponseEntity<UserListResponse> getUsers() {
    return ResponseEntity.ok(UserListResponse.from(userService.getUsers()));
  }

  @GetMapping("/api/happyman/admin/users/admin")
  public ResponseEntity<AdminListResponse> getAdmins() {
    return ResponseEntity.ok(AdminListResponse.from(userService.getAdmins()));
  }

  @PatchMapping("/api/happyman/admin/users/status")
  public ResponseEntity<Void> changeStatus(@RequestParam List<String> uniqueIds, @RequestParam String status) {
    userService.changeStatus(uniqueIds, status);
    return ResponseEntity.ok().build();
  }
}

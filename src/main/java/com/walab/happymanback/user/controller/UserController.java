package com.walab.happymanback.user.controller;

import com.walab.happymanback.user.controller.response.MyProfileResponse;
import com.walab.happymanback.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/api/happyman/my-profile")
  public ResponseEntity<MyProfileResponse> getMyProfile() {
    return ResponseEntity.ok(MyProfileResponse.from(userService.getUser("uniqueId")));
  }
}

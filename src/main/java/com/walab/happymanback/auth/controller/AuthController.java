package com.walab.happymanback.auth.controller;

import com.walab.happymanback.auth.controller.request.TestLoginRequest;
import com.walab.happymanback.auth.controller.response.TestLoginResponse;
import com.walab.happymanback.auth.dto.AuthDto;
import com.walab.happymanback.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/happyman/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/test-login")
  public ResponseEntity<TestLoginResponse> testLogin(@RequestBody TestLoginRequest request) {
    return ResponseEntity.ok(
        TestLoginResponse.from(authService.login(AuthDto.from(request))));
  }
}

package com.walab.happymanback.auth.controller;

import com.walab.happymanback.auth.controller.response.LoginResponse;
import com.walab.happymanback.auth.controller.request.LoginRequest;
import com.walab.happymanback.auth.dto.AuthDto;
import com.walab.happymanback.auth.service.AuthService;
import com.walab.happymanback.auth.service.HisnetLoginService;
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
  private final HisnetLoginService hisnetLoginService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(LoginResponse.from(authService.login(hisnetLoginService.callHisnetLoginApi(AuthDto.from(request)))));
  }
}

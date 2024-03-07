package com.walab.happymanback.auth.controller;

import com.walab.happymanback.auth.controller.request.TestLoginRequest;
import com.walab.happymanback.auth.controller.response.TestLoginResponse;
import com.walab.happymanback.auth.dto.AuthDto;
import com.walab.happymanback.auth.service.AuthService;
import com.walab.happymanback.base.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/happyman/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인 및 회원가입")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/test-login")
  @Operation(summary = "테스트용 로그인", description = "로컬에서 간단하게 돌릴 수 있는 테스트용 로그인 API")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(
            responseCode = "400",
            description = "로그인 실패",
            content =
                @Content(
                    schema = @Schema(implementation = ExceptionResponse.class),
                    examples =
                        @ExampleObject(
                            value =
                                "{\n  \"error\": \"BAD_REQUEST\",\n  \"message\": \"로그인 실패\"\n}")))
      })
  public ResponseEntity<TestLoginResponse> testLogin(@RequestBody TestLoginRequest request) {
    return ResponseEntity.ok(TestLoginResponse.from(authService.login(AuthDto.from(request))));
  }
}

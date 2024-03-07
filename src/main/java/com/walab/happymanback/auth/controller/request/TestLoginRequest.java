package com.walab.happymanback.auth.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
@Schema(description = "테스트용 로그인 요청")
public class TestLoginRequest {
  @Schema(description = "유저 고유 식별자", example = "21900613", requiredMode = Schema.RequiredMode.REQUIRED)
  private String uniqueId;
}

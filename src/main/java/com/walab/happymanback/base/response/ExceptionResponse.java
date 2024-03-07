package com.walab.happymanback.base.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "예외 응답")
public class ExceptionResponse {
  @Schema(description = "에러 코드")
  private String error;

  @Schema(description = "에러 메시지")
  private String message;
}

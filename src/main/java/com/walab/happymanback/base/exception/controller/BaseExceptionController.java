package com.walab.happymanback.base.exception.controller;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.base.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionController {

  @ExceptionHandler(DoNotExistException.class)
  public ResponseEntity<ExceptionResponse> handleDoNotExistException(DoNotExistException e) {
    ExceptionResponse response =
        ExceptionResponse.builder()
            .error(HttpStatus.BAD_REQUEST.toString())
            .message(e.getMessage())
            .build();
    return ResponseEntity.badRequest().body(response);
  }
}

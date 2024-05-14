package com.walab.happymanback.program.exception.controller;

import com.walab.happymanback.base.response.ExceptionResponse;
import com.walab.happymanback.program.exception.NotApplyPeriodException;
import com.walab.happymanback.program.exception.QuotaExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProgramExceptionController{
    @ExceptionHandler(NotApplyPeriodException.class)
    public ResponseEntity<ExceptionResponse> handleNotApplyPeriodException(NotApplyPeriodException e) {
        ExceptionResponse response =
            ExceptionResponse.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(QuotaExceededException.class)
    public ResponseEntity<ExceptionResponse> handleQuotaExceededException(QuotaExceededException e) {
        ExceptionResponse response =
            ExceptionResponse.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

}
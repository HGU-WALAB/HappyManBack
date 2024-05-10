package com.walab.happymanback.participant.exception.controller;

import com.walab.happymanback.base.response.ExceptionResponse;
import com.walab.happymanback.participant.exception.AlreadyAppliedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParticipantExceptionController {
    @ExceptionHandler(AlreadyAppliedException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyAppliedException(AlreadyAppliedException e) {
        ExceptionResponse response =
            ExceptionResponse.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}

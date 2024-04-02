package com.walab.happymanback.file.exception.controller;

import com.walab.happymanback.base.response.ExceptionResponse;
import com.walab.happymanback.file.exception.FileUploadFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionController {
    @ExceptionHandler(FileUploadFailException.class)
    public ResponseEntity<ExceptionResponse> handleFileUploadFailException(FileUploadFailException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

package com.walab.happymanback.auth.exception;

public class WrongTokenException extends RuntimeException {
  public WrongTokenException(String message) {
    super(message);
  }
}

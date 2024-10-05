package com.rawlabs.das.sdk.java.exceptions;

public class DASSdkApiException extends RuntimeException {
  public DASSdkApiException(String message) {
    super(message, null);
  }

  public DASSdkApiException(String message, Throwable cause) {
    super(message, cause);
  }
}

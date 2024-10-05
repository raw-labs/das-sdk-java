package com.rawlabs.das.sdk.java.exceptions;

public class DASSdkValidationException extends DASSdkException {

  public DASSdkValidationException(String message) {
    super(message, null);
  }

  public DASSdkValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}

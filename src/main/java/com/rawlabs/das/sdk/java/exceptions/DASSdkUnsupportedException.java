package com.rawlabs.das.sdk.java.exceptions;

public class DASSdkUnsupportedException extends DASSdkException {

  public DASSdkUnsupportedException() {
    super("unsupported operation");
  }

  public DASSdkUnsupportedException(String message) {
    super(message);
  }
}

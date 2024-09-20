package com.rawlabs.das.sdk.java.exceptions;

import com.rawlabs.utils.core.RawException;

public class DASSdkException extends RawException {

  public DASSdkException(String message) {
    super(message, null);
  }

  public DASSdkException(String message, Throwable cause) {
    super(message, cause);
  }
}

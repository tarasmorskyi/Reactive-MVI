package com.tarasmorskyi.demoappkotlin.utils.errors;

import android.annotation.TargetApi;
import android.os.Build;

public class AppException extends Exception {
  public AppException() {
  }

  public AppException(String message) {
    super(message);
  }

  public AppException(String message, Throwable cause) {
    super(message, cause);
  }

  public AppException(Throwable cause) {
    super(cause);
  }

  @TargetApi(Build.VERSION_CODES.N)
  public AppException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

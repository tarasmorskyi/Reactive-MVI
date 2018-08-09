package com.tarasmorskyi.demoappkotlin.utils.errors;

import android.annotation.TargetApi;
import android.os.Build;

public class AppError extends Exception {
  AppError() {
  }

  public AppError(String message) {
    super(message);
  }

  AppError(String message, Throwable cause) {
    super(message, cause);
  }

  AppError(Throwable cause) {
    super(cause);
  }

  @TargetApi(Build.VERSION_CODES.N)
  public AppError(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

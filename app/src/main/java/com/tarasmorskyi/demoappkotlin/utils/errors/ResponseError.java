package com.tarasmorskyi.demoappkotlin.utils.errors;

public final class ResponseError extends AppError {
  public ResponseError(Throwable error) {
    super(error);
  }

  public ResponseError(int code, String errorBody) {
    super("error: " + code + ", errorBody: " + errorBody);
  }
}

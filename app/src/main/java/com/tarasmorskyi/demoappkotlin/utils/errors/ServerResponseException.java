package com.tarasmorskyi.demoappkotlin.utils.errors;

public class ServerResponseException extends AppException {
  public ServerResponseException(Throwable error) {
    super(error);
  }

  public ServerResponseException(int code, String errorBody) {
    super("error: " + code + ", errorBody: " + errorBody);
  }
}

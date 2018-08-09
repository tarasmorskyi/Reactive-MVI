package com.tarasmorskyi.demoappkotlin.utils.errors;

public class MethodNotImplemented extends Throwable {

  @Override public String getMessage() {
    return "Method not implemented";
  }
}
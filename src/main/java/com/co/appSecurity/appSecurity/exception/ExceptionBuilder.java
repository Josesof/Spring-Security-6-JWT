package com.co.appSecurity.appSecurity.exception;


import lombok.Builder;

@Builder
public class ExceptionBuilder extends Throwable {

  private String withCode;
  private String withMessage;
}

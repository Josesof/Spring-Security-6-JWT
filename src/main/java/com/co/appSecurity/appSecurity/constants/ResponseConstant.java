package com.co.appSecurity.appSecurity.constants;

import lombok.Data;

@Data
public class ResponseConstant {


  /**
   * User
   */
  public static final String USER_URL = "/user";
  public static final String USER_SAVE = "save_user";
  public static final String DELETE_USER = "delete/{idUser}/{email}";
  public static final String UPDATE_USER = "/update-user";
  public static final String ALL_USER = "/all-users";
  public static final String ALL_USER_EMAIL = "get-user/{email}";
  public static final String STATUS = "status";

  public static final String ERROR = "error";

}

package com.co.appSecurity.appSecurity.dtos;

import lombok.Data;

//Esta clase sera la que nos devolvera la informacion con el token y el tipo que tenga este
@Data
public class DtoAuthResponse {
  private String accesToken;
  private String tokenType = "Bearer ";

  public DtoAuthResponse(String accesToken) {
    this.accesToken = accesToken;
  }
}

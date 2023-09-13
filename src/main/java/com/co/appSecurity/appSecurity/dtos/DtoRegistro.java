package com.co.appSecurity.appSecurity.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoRegistro {

  private  String username;
  private String password;
  private String role;

}

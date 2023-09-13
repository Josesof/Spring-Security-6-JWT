package com.co.appSecurity.appSecurity.repository;

import com.co.appSecurity.appSecurity.dtos.DtoRegistro;
import com.co.appSecurity.appSecurity.exception.ExceptionBuilder;
import com.co.appSecurity.appSecurity.exception.MsjException;
import com.co.appSecurity.appSecurity.models.User;

import java.util.List;

public interface IUsuario {

  List<User> listUser() throws MsjException;

  User saveUser(DtoRegistro dtoRegistro) throws MsjException;


  //Metodo para buscar usuario mediante nombre
  User findByForEmail(String username) throws MsjException;

  //Metodo que permite verificar si existe un usuario en la base de datos
  Boolean existsByUsername(String username) throws MsjException;

  public void deletUser(Long idUser, String email) throws MsjException, ExceptionBuilder;


}

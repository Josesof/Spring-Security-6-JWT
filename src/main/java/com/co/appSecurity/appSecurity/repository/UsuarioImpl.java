package com.co.appSecurity.appSecurity.repository;

import com.co.appSecurity.appSecurity.dtos.DtoRegistro;
import com.co.appSecurity.appSecurity.exception.ExceptionBuilder;
import com.co.appSecurity.appSecurity.exception.MsjException;
import com.co.appSecurity.appSecurity.models.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service
public class UsuarioImpl implements IUsuario {

  @Autowired
  private IUsuarioRepository iUsuarioRepository;
  private PasswordEncoder passwordEncoder;
  @Autowired
  private IRolRepository rolRepository;

  @Override
  public List<User> listUser() throws MsjException {
    return (List<User>) iUsuarioRepository.findAll();
  }

  @Override
  public User findByForEmail(String username) throws MsjException {
    return iUsuarioRepository.findByUsername(username).orElse(
      throwException("Error user don't exist ")
    );

  }

  public User saveUser(DtoRegistro dtoRegistro) throws MsjException {
    return existsByUsername(dtoRegistro.getUsername())
      ? throwException("Error: mail " + dtoRegistro.getUsername() + " this mail is used")
      : rolRepository.findByName(dtoRegistro.getRole())
      .map(roles -> {
        if (roles.toString().isEmpty()) {
          try {
            throwException("this role don't exist");
          } catch (MsjException e) {
            throw new RuntimeException(e);
          }
        }
        return User.builder()
          .username(dtoRegistro.getUsername())
          .pasword(passwordEncoder.encode(dtoRegistro.getPassword()))
          .role(Collections.singletonList(roles))
          .build();
      })
      .map(iUsuarioRepository::save)
      .orElseThrow(() -> new MsjException("Error: this user dont'n can save"));
  }


  private <T> T throwException(String message) throws MsjException {
    throw new MsjException(message);
  }



  @Override
  public Boolean existsByUsername(String username) {
    return  iUsuarioRepository.existsByUsername(username);
  }

  @Override
  public void deletUser(Long idUser, String email) throws MsjException, ExceptionBuilder {

    try {
      Optional<User> user = iUsuarioRepository.findByUsername(email);
      if(user.isPresent()) {
        iUsuarioRepository.deletUser(idUser, email);
      }

    } catch (Exception e){
      throw ExceptionBuilder.builder()
        .withCode("Error")
        .withMessage("Error al eliminar registro")
        .build();
    }
   iUsuarioRepository.deleteById(idUser);
  }


}

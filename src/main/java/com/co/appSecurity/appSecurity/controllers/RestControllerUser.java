package com.co.appSecurity.appSecurity.controllers;

import com.co.appSecurity.appSecurity.constants.ResponseConstant;
import com.co.appSecurity.appSecurity.dtos.DtoRegistro;
import com.co.appSecurity.appSecurity.exception.ExceptionBuilder;
import com.co.appSecurity.appSecurity.exception.MsjException;
import com.co.appSecurity.appSecurity.repository.IRolRepository;
import com.co.appSecurity.appSecurity.repository.IUsuarioRepository;
import com.co.appSecurity.appSecurity.repository.UsuarioImpl;
import com.co.appSecurity.appSecurity.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.co.appSecurity.appSecurity.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/** @author Jose Johuar Mosquera */
@Slf4j
@RestController
@RequestMapping(
  value = ResponseConstant.USER_URL,
    produces = {
  MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
  RequestMethod.PUT })
//@RequestMapping("/api/user/")
public class RestControllerUser {

  private PasswordEncoder passwordEncoder;
  private IRolRepository rolRepository;

  private UsuarioImpl usuaroSevice;

  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  public RestControllerUser(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                            IRolRepository rolRepository, IUsuarioRepository userRepository, JwtTokenProvider jwtTokenProvider) {
    this.passwordEncoder = passwordEncoder;
    this.rolRepository = rolRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @GetMapping(ResponseConstant.ALL_USER)
  public ResponseEntity<Object> getListUsers() throws MsjException {
    Map<String, Object> response = new HashMap<>();
    response.put(ResponseConstant.STATUS, usuaroSevice.listUser());
    return new ResponseEntity<>(response,HttpStatus.OK);
  }

  @GetMapping(ResponseConstant.ALL_USER_EMAIL)
  public ResponseEntity<Object> getUserForEmail(@RequestParam(name = "email") String email) throws MsjException {
    Map<String, Object> response = new HashMap<>();
    response.put(ResponseConstant.STATUS, usuaroSevice.findByForEmail(email));
    return new ResponseEntity<>(response,HttpStatus.OK);
  }

  @PostMapping(ResponseConstant.USER_SAVE)
  public ResponseEntity<Object> registryUser(@RequestBody DtoRegistro dtoRegistro)throws MsjException {
    Map<String, Object> response = new HashMap<>();
    response.put(ResponseConstant.STATUS, usuaroSevice.saveUser(dtoRegistro));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping(ResponseConstant.UPDATE_USER)
  public ResponseEntity<Object> updateAdmin(@RequestBody DtoRegistro dtoRegistro)throws MsjException {
    Map<String, Object> response = new HashMap<>();

   return null;
  }
  @DeleteMapping(ResponseConstant.DELETE_USER)
  public ResponseEntity<Object> deletUserForEmail(@RequestParam(name = "idUser") Long idUser, @RequestParam(name = "email") String email) throws MsjException, ExceptionBuilder {
    Map<String, Object> response = new HashMap<>();
    response.put(ResponseConstant.STATUS, "");
    usuaroSevice.deletUser(idUser, email);
    response.put(ResponseConstant.STATUS, "DELETE");
   return new ResponseEntity<>(response, HttpStatus.OK);
  }

}

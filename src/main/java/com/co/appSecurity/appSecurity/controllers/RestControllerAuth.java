package com.co.appSecurity.appSecurity.controllers;

import com.co.appSecurity.appSecurity.dtos.DtoAuthResponse;
import com.co.appSecurity.appSecurity.dtos.DtoLogin;
import com.co.appSecurity.appSecurity.dtos.DtoRegistro;
import com.co.appSecurity.appSecurity.models.Rol;
import com.co.appSecurity.appSecurity.models.Usuario;
import com.co.appSecurity.appSecurity.repository.IRolRepository;
import com.co.appSecurity.appSecurity.repository.IUsuarioRepository;
import com.co.appSecurity.appSecurity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {

  private AuthenticationManager authenticationManager;
  private PasswordEncoder passwordEncoder;
  private IRolRepository rolRepository;

  private IUsuarioRepository userRepository;

  private JwtTokenProvider jwtTokenProvider;

  @Autowired

  public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                            IRolRepository rolRepository, IUsuarioRepository userRepository, JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.rolRepository = rolRepository;
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("register")
  public ResponseEntity<String> registry(@RequestBody DtoRegistro dtoRegistro){
    if(userRepository.existsByUsername(dtoRegistro.getUsername())){
      return  new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
    }
    Usuario usuario =  new Usuario();
    usuario.setUsername(dtoRegistro.getUsername());
    usuario.setPasword(passwordEncoder.encode(dtoRegistro.getPassword()));
    Rol roles = rolRepository.findByName("USER").get();
    usuario.setRoles(Collections.singletonList(roles));
    userRepository.save(usuario);
return  new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
  }

  @PostMapping("registerAdmin")
  public ResponseEntity<String> registryAdmin(@RequestBody DtoRegistro dtoRegistro) {
    if(userRepository.existsByUsername(dtoRegistro.getUsername())){
      return  new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
    }
    Usuario usuario =  new Usuario();
    usuario.setUsername(dtoRegistro.getUsername());
    usuario.setPasword(passwordEncoder.encode(dtoRegistro.getPassword()));
    Rol roles = rolRepository.findByName("USER").get();
    usuario.setRoles(Collections.singletonList(roles));
    userRepository.save(usuario);
    return  new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
  }

  //Metodo para loguar un usuario y obtener el toke
  @PostMapping("login")
  public ResponseEntity<DtoAuthResponse> login(@RequestBody DtoLogin dtoLogin) {
    Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          dtoLogin.getUsername(), dtoLogin.getPassword()
        ));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenProvider.generateToken(authentication);
     return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
  }
}

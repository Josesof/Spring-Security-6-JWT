package com.co.appSecurity.appSecurity.security;

import com.co.appSecurity.appSecurity.models.Rol;
import com.co.appSecurity.appSecurity.models.Usuario;
import com.co.appSecurity.appSecurity.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
  
  private IUsuarioRepository usuarioRepo;

  @Autowired
  public CustomUsersDetailsService(IUsuarioRepository usuarioRepo) {
    this.usuarioRepo = usuarioRepo;
  }
//Metodo que permite traer una lista de autoridades por medio de una lista de roles
  public Collection<GrantedAuthority> mapToAuthority(List<Rol> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }
//Metodo que pertmite traer un usuario con todos sus datos
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    return new User(usuario.getUsername(), usuario.getPasword(), mapToAuthority(usuario.getRoles()));
  }
}

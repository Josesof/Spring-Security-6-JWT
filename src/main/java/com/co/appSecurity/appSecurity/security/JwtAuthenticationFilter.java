package com.co.appSecurity.appSecurity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//La funcion de esta clase sera validar lainformacion del token y si esto es exitoso, establecera la autenticacion de un usuario en la solicitud
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private CustomUsersDetailsService customUsersDetailsService;
  @Autowired
  private  JwtTokenProvider jwtTokenProvider;

  /*Con el siguiente metodo extraemos el token JWT de la cabecera de nuestra peticion("Authorization"),
  luego lo validamos y finalmente se retorna
   */
  private String getTokenOfSolicitud(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
      return  bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  //Mecanismo para invocar el siguiente  filtro en la siguiente cadena de filtros
                                  FilterChain filterChain) throws ServletException, IOException {
    String token = getTokenOfSolicitud(request);
    //Valida la informacion del token
    if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
      //Asignamos el nombre de usuario contenido en el objeto (token) y lo pasamos a nuestra variable username
      String username = jwtTokenProvider.getUsernameofJwt(token);
      //Luego creamos el objeto userDetails el cual contendra todos los detalles de nuestro username, nombre, roles segun el metodo loadUserByUsername
      UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);
      //Cargamos una lista de String con los roles alojados en la BD
      List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
      //Comprobamos que el usuario autenticado posee alguno de los siguientes roles asignados
      if(userRoles.contains("USER" )  ||  userRoles.contains("ADMIN")){
        // Creamos el objeto UsernamePasswordAuthenticationToken el cual contendra   detalles de authenticacion del usuario
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
          null, userDetails.getAuthorities());
        //establece informacion adcional de la autenticacion, como por ejemplo la ip del usuario, o el agente de usuario para la solicitud etc
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //Establece el objeto anterior (autenticacion del usuario)  en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
  }
    //Permite que la solicitud continue hacia el siguiente filtro en la cadena de filtro
    filterChain.doFilter(request, response);
}
}

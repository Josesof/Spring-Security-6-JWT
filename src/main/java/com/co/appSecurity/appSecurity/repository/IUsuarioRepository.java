package com.co.appSecurity.appSecurity.repository;

import com.co.appSecurity.appSecurity.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository  extends JpaRepository <Usuario, Long>{
  //Metodo para buscar usuario mediante nombre
  Optional<Usuario> findByUsername(String username);

  //Metodo que permite verificar si existe un usuario en la base de datos
  Boolean existsByUsername(String username);
}

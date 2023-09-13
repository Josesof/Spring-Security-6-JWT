package com.co.appSecurity.appSecurity.repository;

import com.co.appSecurity.appSecurity.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
  //Metodo para buscar roles mediante el  nombre
  Optional<Rol> findByName(String name);
}

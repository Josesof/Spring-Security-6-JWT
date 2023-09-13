package com.co.appSecurity.appSecurity.repository;

import com.co.appSecurity.appSecurity.exception.ExceptionBuilder;
import com.co.appSecurity.appSecurity.exception.MsjException;
import com.co.appSecurity.appSecurity.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository  extends CrudRepository<User, Long> {
  //Metodo para buscar usuario mediante nombre

  /**
   *
   * @param username
   * @return
   *
   *  @Query(
   *     value =
   *         "SELECT p.id_parametric ,p.code ,p.description ,p.code_parent, "
   *             + "p.active ,p.creation_date ,p.modification_date ,p.company_id "
   *             + "FROM  parametric_shift p "
   *             + "WHERE p.code = :idBlockingReasons "
   *             + "AND p.company_id = :companyId ",
   *     nativeQuery = true
   *   )
   */
  Optional<User> findByUsername(String username);

  //Metodo que permite verificar si existe un usuario en la base de datos
  Boolean existsByUsername(String username);

  @Query(
    value =
      "DELETE FROM users user WHERE user.id_user = :idUser "
        + "and user.name = :email ",
    nativeQuery = true
  )
  public void deletUser(@Param("idUser") Long idUser, @Param("email") String email) throws MsjException, ExceptionBuilder;
}

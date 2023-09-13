package com.co.appSecurity.appSecurity.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user")
  private Long idUser;
  private String username;
  private String pasword;
  private String address;

  @Column(name = "date_create")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @CreatedDate
  Date dateCreate;

  @Column(name = "date_end")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date dateEnd;

  //Usamos fetchType en EAGER para que cada vez que se acceda o se extraiga un usuario de la BD, este se traiga todos sus roles
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  /*Con JoinTable estaremos creando una tabla que unirá la tabla de usuario y role, con lo cual tendremos un total de 3 tablas
    relacionadas en la tabla "usuarios_roles", a través de sus columnas usuario_id que apuntara al ID de la tabla usuario
    y role_id que apuntara al Id de la tabla role */
  @JoinTable(name = "user_roles",  joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user")
  ,inverseJoinColumns = @JoinColumn(name = "role_id",  referencedColumnName = "id_role"))
  private List<Rol> role = new ArrayList<>();

  @OneToMany(targetEntity = Bill.class, fetch = FetchType.LAZY, mappedBy = "user")
  private List<Bill> bill = new ArrayList<>();



}

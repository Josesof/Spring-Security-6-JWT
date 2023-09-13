package com.co.appSecurity.appSecurity.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "devolucion_bill")
public class DevolutionBill implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_devolucion_bill")
  private Long idDevolutionBill;



  @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, mappedBy = "bill")
  private List<Product> product = new ArrayList<>();

  @OneToOne(targetEntity = Bill.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_bill")
  private Bill bill;

  @OneToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_usuario")
  private User usuario;

  @OneToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_methoPay")
  private MethoPay methoPay;

  @Column(name = "date_create")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date dateCreate;

  @Column(name = "date_end")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date dateEnd;
}

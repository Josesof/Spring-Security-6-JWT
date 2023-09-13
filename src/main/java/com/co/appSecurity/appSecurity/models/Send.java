package com.co.appSecurity.appSecurity.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sends")
public class Send implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_send")
  private Long idSend;
  private Long uuid;

  @Column(name = "date_create")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date dateCreate;
  @Column(name = "date_end")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date dateEnd;

  @OneToOne(targetEntity = Bill.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_bill")
  private Bill bill;

  @OneToOne(targetEntity = Bill.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_usuario")
  private User usuario;

  @OneToOne(targetEntity = Bill.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_methoPay")
  private MethoPay methoPay;
}

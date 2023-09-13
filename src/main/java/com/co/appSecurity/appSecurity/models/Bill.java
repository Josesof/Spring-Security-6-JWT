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
@Table(name = "bill")
public class Bill implements Serializable {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id_bill")
 private Long idBill;
 private Long total;
 @Column(name = "estado_bill")
 private String estadoBill;

 @Column(name = "date_create")
 @Temporal(TemporalType.DATE)
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 Date dateCreate;

 @Column(name = "date_end")
 @Temporal(TemporalType.DATE)
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 Date dateEnd;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "usuario_id")
 private User user;

 @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, mappedBy = "bill")
 private List<Product> product = new ArrayList<>();

}

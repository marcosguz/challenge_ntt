package com.bank.wealthstream.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_CUS", nullable = false,updatable = false)
    private String idCus;

    @ManyToOne
    @JoinColumn(name = "ID_PER", foreignKey = @ForeignKey(name = "FK_CUSTOMER_PERSON"))
    private Person idPer;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name = "STATE",nullable = false)
    private Boolean state;

}

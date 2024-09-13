package com.bank.wealthstream.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_ACC", nullable = false,updatable = false)
    private String idAcc;

    @ManyToOne
    @JoinColumn(name = "ID_CUS", foreignKey = @ForeignKey(name = "FK_ACCOUNT_CUSTOMER"))
    private Customer idCus;

    @Column(name = "ACCOUNT_NUMBER",nullable = false)
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE",nullable = false)
    private String accountType;

    @Column(name = "INITIAL_BALANCE",nullable = false)
    private Double initialBalance;

    @Column(name = "STATE",nullable = false)
    private Boolean state;
}

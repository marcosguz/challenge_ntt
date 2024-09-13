package com.bank.wealthstream.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCOUNT_MOVEMENTS")
public class AccountMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_MVN", nullable = false,updatable = false)
    private String idMvn;

    @ManyToOne
    @JoinColumn(name = "ID_ACC", foreignKey = @ForeignKey(name = "FK_ACCOUNT_MOVEMENTS_ACCOUNT"))
    private Account idAcc;

    @Column(name = "DATE",nullable = false)
    private LocalDateTime date;

    @Column(name = "MOVEMENT_TYPE",nullable = false)
    private String movementType;

    @Column(name = "VALUE",nullable = false)
    private Double value;

    @Column(name = "BALANCE",nullable = false)
    private Double balance;
}

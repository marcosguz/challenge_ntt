package com.bank.wealthstream.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_PER", nullable = false,updatable = false)
    private String idPer;

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "SURNAME",nullable = false)
    private String surname;

    @Column(name = "GENDER",nullable = false)
    private String gender;

    @Column(name = "AGE",nullable = false)
    private int age;

    @Column(name = "IDENTIFICATION",nullable = false)
    private String identification;

    @Column(name = "ADDRESS",nullable = false)
    private String address;

    @Column(name = "PHONE",nullable = false)
    private String phone;
}

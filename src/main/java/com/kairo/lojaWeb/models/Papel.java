package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "papel")
@Data
public class Papel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

}

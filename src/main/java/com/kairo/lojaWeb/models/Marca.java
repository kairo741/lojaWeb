package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "marca")
@Data
public class Marca {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

}

package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
@Data
public class Categoria {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

}

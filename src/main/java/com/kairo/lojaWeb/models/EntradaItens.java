package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "entrada_itens")
@Data
public class EntradaItens {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private EntradaProduto entrada;

    @ManyToOne
    private Produto produto;
    private Double quantidade;
    private Double valorProduto = 0.;
    private Double valorVenda = 0.;

}

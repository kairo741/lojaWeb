package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "produto")
@Data
public class Produto {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Double valorVenda;
    private Double quantidadeEstoque = 0.;
    private String imageName;
    @Column(name = "produtoInfo", length = 500, columnDefinition = "TEXT")
    private String produtoInfo;
    @ManyToOne
    Categoria categoria;
    @ManyToOne
    Marca marca;

}

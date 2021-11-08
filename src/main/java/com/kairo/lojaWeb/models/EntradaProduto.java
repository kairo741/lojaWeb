package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entrada_produto")
@Data
public class EntradaProduto {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Funcionario funcionario;
    private Date dataEntrada = new Date();
    private String observacao;
    private String fornecedor; // todo - criar entidade para fornecedor


}

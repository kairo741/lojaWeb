package com.kairo.lojaWeb.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "permissao")
@Data
public class Permissao {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dataCadastro = new Date();

    @ManyToOne
    private Funcionario funcionario;

    @ManyToOne
    private Papel papel;
}

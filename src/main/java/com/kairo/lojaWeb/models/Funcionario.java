package com.kairo.lojaWeb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Funcionario {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double salarioBruto;
    private String cpf;
    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    @Temporal(TemporalType.DATE)
    private Date dataSaida;
    private String cargo;
    @ManyToOne
    private Cidade cidade;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String uf;
    private String cep;
    private String email;
    private String senha;
    private String verificationCode;
}

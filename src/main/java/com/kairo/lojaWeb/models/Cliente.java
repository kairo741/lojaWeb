package com.kairo.lojaWeb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@Data
public class Cliente {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    @NotBlank
    @Size(max = 200)
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    private String senha;
    @ManyToOne
    private Cidade cidade;


}

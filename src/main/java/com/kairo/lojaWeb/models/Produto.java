package com.kairo.lojaWeb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "produto_photos")
    @Column(name = "photos") // Column name in produto_photos
    private List<String> photos;

}

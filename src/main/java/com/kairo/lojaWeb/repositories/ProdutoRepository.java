package com.kairo.lojaWeb.repositories;

import com.kairo.lojaWeb.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAllByCategoria_NomeAndDescricaoAndMarca_Nome(String categoria_nome, String descricao, String marca_nome);

}

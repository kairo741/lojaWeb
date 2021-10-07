package com.kairo.lojaWeb.repositories;

import com.kairo.lojaWeb.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}

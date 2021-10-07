package com.kairo.lojaWeb.repositories;

import com.kairo.lojaWeb.models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    List<Cidade> findAll();

}

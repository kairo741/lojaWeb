package com.kairo.lojaWeb.repositories;

import com.kairo.lojaWeb.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findAll();

    Optional<Funcionario> findByEmail(String email);
}

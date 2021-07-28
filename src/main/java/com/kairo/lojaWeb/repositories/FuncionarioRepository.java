package com.kairo.lojaWeb.repositories;

import com.kairo.lojaWeb.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}

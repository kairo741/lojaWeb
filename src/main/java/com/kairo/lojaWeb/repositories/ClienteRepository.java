package com.kairo.lojaWeb.repositories;

import com.kairo.lojaWeb.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

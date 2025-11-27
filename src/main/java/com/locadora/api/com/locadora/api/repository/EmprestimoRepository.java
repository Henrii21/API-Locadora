package com.locadora.api.repository;

import com.locadora.api.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {


    long countByItemIdAndFinalizadoFalse(Long itemId);
}

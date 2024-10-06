package com.projeto.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projeto.shopee.entities.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {

    boolean existsByUsuarioId(Long usuarioId);  
}

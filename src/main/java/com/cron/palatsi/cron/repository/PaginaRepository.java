package com.cron.palatsi.cron.repository;

import com.cron.palatsi.cron.entity.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaginaRepository extends JpaRepository<Pagina, String> {
    @Query(value = "SELECT p FROM Pagina p WHERE p.estado = 'ACTIVO'")
    List<Pagina> findByPaginaAndEstado();
}

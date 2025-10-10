package com.cron.palatsi.cron.repository;

import com.cron.palatsi.cron.dto.IProceso;
import com.cron.palatsi.cron.entity.Proceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcesoRepository extends JpaRepository<Proceso,Long> {
    List<Proceso> findAllBy();

    List<Proceso> findByPagina(String pagina);
    /*@Query("SELECT T FROM Proceso T WHERE T.sku ='7707999184010' AND T.pagina =:pagina")
    List<Proceso> findByPagina(String pagina);*/
}

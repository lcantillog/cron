package com.cron.palatsi.cron.repository;

import com.cron.palatsi.cron.entity.Articulo;
import com.cron.palatsi.cron.entity.IdClass.ArticuloId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, ArticuloId> {

    boolean existsArticuloBySku(String sku);

    @Query(value = "SELECT ISNULL((SELECT L.LPPRECIO " +
            "  FROM LISTPRE2 L " +
            " WHERE L.LPCODEMP = 1 " +
            "   AND L.LPCODPAR = :sku " +
            "   AND L.LPCODTPR IN (14)),0)  ",nativeQuery = true)
    Double getPrecioArticulo(String sku);
    @Query(value = " SELECT ISNULL((SELECT L.LOCANART " +
                    "  FROM PRESARTI P " +
                    " INNER JOIN LOCALIZA L" +
                    "    ON L.LOCODEMP = P.PACODEMP " +
                    "   AND L.LOCODART = P.PACODART " +
                    " WHERE P.PACODEMP = 1 " +
                    "   AND L.LOCODBOD = 8" +
                    "   AND P.PACODIGO = :sku ),0) ",nativeQuery = true)
    Integer getCantidadArticulo(String sku);
}

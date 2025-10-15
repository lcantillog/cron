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
            " WHERE L.LPCODEMP = :empresa " +
            "   AND L.LPCODPAR = :sku " +
            "   AND L.LPCODTPR IN (:listaPrecio)),0)  ",nativeQuery = true)
    Double getPrecioArticulo(Integer empresa, Integer listaPrecio, String sku);
    @Query(value = " SELECT ISNULL((SELECT L.LOCANART " +
                    "  FROM PRESARTI P " +
                    " INNER JOIN LOCALIZA L" +
                    "    ON L.LOCODEMP = P.PACODEMP " +
                    "   AND L.LOCODART = P.PACODART " +
                    " WHERE P.PACODEMP = :empresa " +
                    "   AND L.LOCODBOD = :bodega " +
                    "   AND P.PACODIGO = :sku ),0) ",nativeQuery = true)
    Integer getCantidadArticulo(Integer empresa, Integer bodega, String sku);
}

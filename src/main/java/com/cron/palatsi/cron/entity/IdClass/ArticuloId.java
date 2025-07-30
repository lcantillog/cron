package com.cron.palatsi.cron.entity.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticuloId implements Serializable {
    private String sku;
    private String shopify;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticuloId articuloId = (ArticuloId) o;
        return Objects.equals(sku, articuloId.sku)
                && Objects.equals(shopify, articuloId.shopify) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, shopify);
    }
}

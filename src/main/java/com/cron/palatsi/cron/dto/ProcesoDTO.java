package com.cron.palatsi.cron.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ProcesoDTO {
    private String sku;
    private double price;
    private double sale_price;
    private int stock;
}

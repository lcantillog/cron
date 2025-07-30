package com.cron.palatsi.cron.beans;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Variants implements Serializable {
    private Double price;
    private Double compare_at_price;
    private String sku;
    private int inventory_quantity;
}

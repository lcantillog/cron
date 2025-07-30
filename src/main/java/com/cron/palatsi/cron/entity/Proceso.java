package com.cron.palatsi.cron.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PROCEPHP")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proceso {

    @Id
    @Column(name = "PHCODIGO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "PHFECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "PHCODSKU")
    private String sku;

    @Column(name = "PHCODSHO")
    private String shopify;

    @Column(name = "PHCANTID")
    private int cantidad;

    @Column(name = "PHPRECIO")
    private double precio;

    @Column(name = "PHPREANT", nullable = true)
    private Double precioAnterior;

    @Column(name = "PHVARIAN")
    private boolean variante;

    @Column(name = "PHPAGINA")
    private String pagina;
}

package com.cron.palatsi.cron.entity;

import com.cron.palatsi.cron.entity.IdClass.ArticuloId;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.RowId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ARTISHOP")
@AllArgsConstructor
@NoArgsConstructor
public class Articulo {
    @Column(name = "ASCODSKU")
    private String sku;
    @Column(name = "ASCODSHO")
    private String shopify;
    @Column(name = "ASCANTID")
    private int cantidad;
    @Column(name = "ASPRECIO")

    private double precio;
    @Column(name = "ASCODLPR")
    private int listaPrecio;
    @Column(name = "ASVARIAN")
    private boolean variante;
    @Column(name = "ASPREANT")
    private double precioAnterio;
    @Column(name = "ASPAGINA")
    private String pagina;
    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "rowguid" , columnDefinition="uniqueidentifier")
    private String id;
}

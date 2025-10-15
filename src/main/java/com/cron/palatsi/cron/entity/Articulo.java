package com.cron.palatsi.cron.entity;

import com.cron.palatsi.cron.entity.IdClass.ArticuloId;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.RowId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ARTISHOP")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ArticuloId.class) // Composición de clave primaria
public class Articulo implements Serializable {

    @Id
    @Column(name = "ASCODSKU")
    private String sku;
    @Id
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

}

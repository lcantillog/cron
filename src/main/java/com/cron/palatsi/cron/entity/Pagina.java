package com.cron.palatsi.cron.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PROCEPAG")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagina {


    @Id
    @Column(name = "PPCODIGO")
    private String id;
    @Column(name = "PPNOMBRE")
    private String nombre;
    @Column(name = "PPESTADO")
    private String estado;
    @Column(name = "PPELIMIN")
    private boolean eliminar;
    @Column(name = "PPHISTOR")
    private boolean historico;
    @Column(name = "PPNUMINT")
    private Integer intentos;
    @Column(name = "PPFLUWEB")
    private String flujoWeb;
}

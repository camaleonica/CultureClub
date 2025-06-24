package com.cultureclub.cclub.entity;

import java.sql.Date;
import java.util.List;

import com.cultureclub.cclub.entity.Ciudad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario usuarioOrganizador;

    @Column
    private String nombre;

    @Column
    private boolean entrada;

    @Column
    private int precio;

    @Column
    private Date inicio;

    @Column
    private Date fin;

    @Enumerated(EnumType.STRING)
    @Column
    private Ciudad ciudad;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @Column
    private String imagenUrl;

    @Column
    private String detalleUrl;

    @ManyToMany
    @JoinTable(name = "evento_seguidores", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> seguidores;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaseEvento clase;

    @Column
    private int clasificacion;

    @OneToMany(mappedBy = "evento")
    private List<Entrada> entradas;
}

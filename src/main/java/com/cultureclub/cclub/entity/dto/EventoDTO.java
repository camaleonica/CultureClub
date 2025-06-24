package com.cultureclub.cclub.entity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EventoDTO {
    long idEvento;
    long idOrganizador;
    String nombre;
    boolean entrada;
    int precio;
    Date inicio;
    Date fin;
    String clase;

    String ciudad;

    Double latitud;

    Double longitud;

    String imagenUrl;

    String detalleUrl;
}
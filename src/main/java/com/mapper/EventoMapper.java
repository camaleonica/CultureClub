package com.mapper;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;

public class EventoMapper {

    public static EventoDTO toDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setIdOrganizador(evento.getUsuarioOrganizador().getIdUsuario());
        dto.setNombre(evento.getNombre());
        dto.setEntrada(evento.isEntrada());
        dto.setPrecio(evento.getPrecio());
        dto.setInicio(evento.getInicio());
        dto.setFin(evento.getFin());
        dto.setClase(evento.getClase().name());
        if (evento.getCiudad() != null) {
            dto.setCiudad(evento.getCiudad().name());
        }
        dto.setLatitud(evento.getLatitud());
        dto.setLongitud(evento.getLongitud());
        dto.setImagenUrl(evento.getImagenUrl());
        dto.setDetalleUrl(evento.getDetalleUrl());
        return dto;
    }

    public static Evento toEntity(EventoDTO dto, com.cultureclub.cclub.entity.Usuario organizador) {
        Evento evento = new Evento();
        evento.setIdEvento(dto.getIdEvento());
        evento.setUsuarioOrganizador(organizador);
        evento.setNombre(dto.getNombre());
        evento.setEntrada(dto.isEntrada());
        evento.setPrecio(dto.getPrecio());
        evento.setInicio(dto.getInicio());
        evento.setFin(dto.getFin());
        if (dto.getCiudad() != null) {
            evento.setCiudad(com.cultureclub.cclub.entity.Ciudad.valueOf(dto.getCiudad()));
        }
        evento.setLatitud(dto.getLatitud());
        evento.setLongitud(dto.getLongitud());
        evento.setImagenUrl(dto.getImagenUrl());
        evento.setDetalleUrl(dto.getDetalleUrl());
        if (dto.getClase() != null) {
            evento.setClase(com.cultureclub.cclub.entity.ClaseEvento.valueOf(dto.getClase()));
        }
        return evento;
    }
}

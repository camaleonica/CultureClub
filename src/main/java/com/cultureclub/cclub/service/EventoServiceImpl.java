package com.cultureclub.cclub.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.ClaseEvento;
import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.repository.EventoRepository;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private GestorUsuarioService usuarioService;

    @Override
    public Page<Evento> getEventos(int page, int size) {
        return eventoRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Evento createEvento(EventoDTO entity, Long idUsuario) throws Exception {
        List<Evento> eventos = eventoRepository.findByNombre(entity.getNombre());
        if (!eventos.isEmpty()) {
            throw new IllegalArgumentException("Evento con el nombre '" + entity.getNombre() + "' ya existe.");
        }

        if (entity.getInicio() != null && entity.getFin() != null && entity.getInicio().after(entity.getFin())) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        Evento evento = new Evento();
        evento.setNombre(entity.getNombre());
        evento.setEntrada(entity.isEntrada());
        evento.setPrecio(entity.getPrecio());
        evento.setInicio(entity.getInicio());
        evento.setFin(entity.getFin());
        if (entity.getCiudad() != null) {
            evento.setCiudad(com.cultureclub.cclub.entity.Ciudad.valueOf(entity.getCiudad()));
        }
        evento.setLatitud(entity.getLatitud());
        evento.setLongitud(entity.getLongitud());
        evento.setImagenUrl(entity.getImagenUrl());
        evento.setDetalleUrl(entity.getDetalleUrl());
        evento.setClase(ClaseEvento.valueOf(entity.getClase()));
        // Buscar y asignar el organizador
        Usuario organizador = usuarioService.getUsuarioById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Organizador no encontrado"));
        evento.setUsuarioOrganizador(organizador);
        return eventoRepository.save(evento);
    }

}
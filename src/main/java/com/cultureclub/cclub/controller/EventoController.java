package com.cultureclub.cclub.controller;

import org.springframework.data.domain.Page;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.service.EventoService;
import com.mapper.EventoMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping(value = "/{idUsuario}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<EventoDTO> publicarEvento(@RequestPart("evento") EventoDTO entity,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @PathVariable Long idUsuario) throws Exception {
        if (imagen != null && !imagen.isEmpty()) {
            Path uploadDir = Paths.get("src/main/resources/static/uploads");
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(imagen.getOriginalFilename());
            Files.copy(imagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            entity.setImagenUrl("/uploads/" + imagen.getOriginalFilename());
        }
        EventoDTO evento = EventoMapper.toDTO(eventoService.createEvento(entity, idUsuario));
        return ResponseEntity.created(URI.create("/eventos/" + evento.getIdEvento())).body(evento);
    }

    @GetMapping("")
    public ResponseEntity<Page<EventoDTO>> getEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventos(page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}

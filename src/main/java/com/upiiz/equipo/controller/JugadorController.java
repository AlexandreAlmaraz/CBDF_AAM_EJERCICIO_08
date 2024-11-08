package com.upiiz.equipo.controller;

import com.upiiz.equipo.dto.CustomResponse;
import com.upiiz.equipo.models.Jugador;
import com.upiiz.equipo.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Jugador>>> getAll() {
        List<Jugador> jugadores = jugadorService.getJugadores();
        Link selfLink = linkTo(JugadorController.class).withSelfRel();
        return !jugadores.isEmpty() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Jugadores encontrados", jugadores, List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron jugadores", jugadores, List.of(selfLink)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Jugador>> getById(@PathVariable Long id) {
        Optional<Jugador> jugador = jugadorService.getJugadorById(id);
        Link selfLink = linkTo(JugadorController.class).withSelfRel();
        return jugador.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Jugador encontrado", jugador.get(), List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Jugador no encontrado", null, List.of(selfLink)));
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Jugador>> create(@RequestBody Jugador jugador) {
        Jugador nuevoJugador = jugadorService.createJugador(jugador);
        Link selfLink = linkTo(JugadorController.class).withSelfRel();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomResponse<>(1, "Jugador creado exitosamente", nuevoJugador, List.of(selfLink)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Jugador>> update(@PathVariable Long id, @RequestBody Jugador jugador) {
        jugador.setId(id);
        Optional<Jugador> jugadorExistente = jugadorService.getJugadorById(id);
        Link selfLink = linkTo(JugadorController.class).withSelfRel();
        return jugadorExistente.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Jugador actualizado", jugadorService.updateJugador(jugador), List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Jugador no encontrado", null, List.of(selfLink)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> delete(@PathVariable Long id) {
        Optional<Jugador> jugadorExistente = jugadorService.getJugadorById(id);
        Link selfLink = linkTo(JugadorController.class).withSelfRel();
        return jugadorExistente.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Jugador eliminado", null, List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Jugador no encontrado", null, List.of(selfLink)));
    }
}

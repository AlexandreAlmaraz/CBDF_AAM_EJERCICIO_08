package com.upiiz.equipo.controller;


import com.upiiz.equipo.dto.CustomResponse;
import com.upiiz.equipo.models.Entrenador;
import com.upiiz.equipo.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Entrenador>>> getEntrenadores() {
        List<Entrenador> entrenadores;
        Link selfLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            entrenadores = entrenadorService.getEntrenadores();
            if (!entrenadores.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Entrenadores encontrados", entrenadores, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron entrenadores", entrenadores, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Entrenador>> getEntrenadorById(@PathVariable Long id) {
        Link selfLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<Entrenador> entrenador = entrenadorService.getEntrenadorById(id);
            if (entrenador.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Entrenador encontrado", entrenador.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Entrenador no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Entrenador>> createEntrenador(@RequestBody Entrenador entrenador) {
        Link selfLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Entrenador nuevoEntrenador = entrenadorService.createEntrenador(entrenador);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Entrenador creado exitosamente", nuevoEntrenador, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear el entrenador", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Entrenador>> updateEntrenador(
            @PathVariable Long id, @RequestBody Entrenador entrenador) {
        Link selfLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            entrenador.setId(id);
            if (entrenadorService.getEntrenadorById(id).isPresent()) {
                Entrenador entrenadorActualizado = entrenadorService.updateEntrenador(entrenador);
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Entrenador actualizado exitosamente", entrenadorActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Entrenador no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar el entrenador", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteEntrenador(@PathVariable Long id) {
        Link selfLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (entrenadorService.getEntrenadorById(id).isPresent()) {
                entrenadorService.deleteEntrenador(id);
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Entrenador eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Entrenador no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar el entrenador", null, links));
        }
    }
}
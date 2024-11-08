package com.upiiz.equipo.controller;

import com.upiiz.equipo.dto.CustomResponse;
import com.upiiz.equipo.models.EquipoCompetencia;
import com.upiiz.equipo.service.EquipoCompetenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/equipos_competencia")
public class EquipoCompetenciaController {

    @Autowired
    private EquipoCompetenciaService equipoCompetenciaService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<EquipoCompetencia>>> getAll() {
        List<EquipoCompetencia> items = equipoCompetenciaService.getAllEquipoCompetencias();
        Link selfLink = linkTo(EquipoCompetenciaController.class).withSelfRel();
        return !items.isEmpty() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Relaciones encontradas", items, List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron relaciones", items, List.of(selfLink)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<EquipoCompetencia>> getById(@PathVariable Long id) {
        Optional<EquipoCompetencia> item = equipoCompetenciaService.getEquipoCompetenciaById(id);
        Link selfLink = linkTo(EquipoCompetenciaController.class).withSelfRel();
        return item.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Relación encontrada", item.get(), List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Relación no encontrada", null, List.of(selfLink)));
    }

    @PostMapping
    public ResponseEntity<CustomResponse<EquipoCompetencia>> create(@RequestBody EquipoCompetencia equipoCompetencia) {
        EquipoCompetencia nuevo = equipoCompetenciaService.createEquipoCompetencia(equipoCompetencia);
        Link selfLink = linkTo(EquipoCompetenciaController.class).withSelfRel();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomResponse<>(1, "Relación creada", nuevo, List.of(selfLink)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<EquipoCompetencia>> update(@PathVariable Long id, @RequestBody EquipoCompetencia equipoCompetencia) {
        equipoCompetencia.setId(id);
        Optional<EquipoCompetencia> itemExistente = equipoCompetenciaService.getEquipoCompetenciaById(id);
        Link selfLink = linkTo(EquipoCompetenciaController.class).withSelfRel();
        return itemExistente.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Relación actualizada", equipoCompetenciaService.updateEquipoCompetencia(equipoCompetencia), List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Relación no encontrada", null, List.of(selfLink)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> delete(@PathVariable Long id) {
        Optional<EquipoCompetencia> itemExistente = equipoCompetenciaService.getEquipoCompetenciaById(id);
        Link selfLink = linkTo(EquipoCompetenciaController.class).withSelfRel();
        return itemExistente.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Relación eliminada", null, List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Relación no encontrada", null, List.of(selfLink)));
    }
}

package com.upiiz.equipo.controller;

import com.upiiz.equipo.dto.CustomResponse;
import com.upiiz.equipo.models.Liga;
import com.upiiz.equipo.service.LigaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/ligas")
@Tag(name = "Liga", description = "API para gestionar ligas deportivas")
public class LigaController {

    @Autowired
    private LigaService ligaService;

    @Operation(summary = "Obtener todas las ligas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligas encontradas",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron ligas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<CustomResponse<List<Liga>>> getLigas() {
        List<Liga> ligas = ligaService.getLigas();
        Link selfLink = linkTo(LigaController.class).withSelfRel();
        return !ligas.isEmpty() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Ligas encontradas", ligas, List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron ligas", ligas, List.of(selfLink)));
    }

    @Operation(summary = "Obtener una liga por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liga encontrada"),
            @ApiResponse(responseCode = "404", description = "Liga no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> getLigaById(@Parameter(description = "ID de la liga a buscar") @PathVariable Long id) {
        Optional<Liga> liga = ligaService.getLigaById(id);
        Link selfLink = linkTo(LigaController.class).withSelfRel();
        return liga.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Liga encontrada", liga.get(), List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Liga no encontrada", null, List.of(selfLink)));
    }

    @Operation(summary = "Crear una nueva liga")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Liga creada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al crear la liga")
    })
    @PostMapping
    public ResponseEntity<CustomResponse<Liga>> createLiga(@RequestBody Liga liga) {
        Liga nuevaLiga = ligaService.createLiga(liga);
        Link selfLink = linkTo(LigaController.class).withSelfRel();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomResponse<>(1, "Liga creada exitosamente", nuevaLiga, List.of(selfLink)));
    }

    @Operation(summary = "Actualizar una liga")
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> updateLiga(@PathVariable Long id, @RequestBody Liga liga) {
        liga.setId(id);
        Optional<Liga> ligaExistente = ligaService.getLigaById(id);
        Link selfLink = linkTo(LigaController.class).withSelfRel();
        return ligaExistente.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Liga actualizada exitosamente", ligaService.updateLiga(liga), List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Liga no encontrada", null, List.of(selfLink)));
    }

    @Operation(summary = "Eliminar una liga")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteLiga(@PathVariable Long id) {
        Optional<Liga> ligaExistente = ligaService.getLigaById(id);
        Link selfLink = linkTo(LigaController.class).withSelfRel();
        return ligaExistente.isPresent() ?
                ResponseEntity.ok(new CustomResponse<>(1, "Liga eliminada exitosamente", null, List.of(selfLink))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Liga no encontrada", null, List.of(selfLink)));
    }
}

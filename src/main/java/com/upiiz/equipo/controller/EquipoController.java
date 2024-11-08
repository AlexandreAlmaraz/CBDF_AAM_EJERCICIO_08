package com.upiiz.equipo.controller;


import com.upiiz.equipo.dto.CustomResponse;
import com.upiiz.equipo.models.Equipo;
import com.upiiz.equipo.service.EquipoService;
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
@RequestMapping("/api/equipos")
@Tag(name = "Equipo", description = "API para gestionar equipos deportivos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @Operation(summary = "Obtener todos los equipos",
            description = "Retorna una lista de todos los equipos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipos encontrados",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron equipos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping
    public ResponseEntity<CustomResponse<List<Equipo>>> getEquipos() {
        List<Equipo> equipos;
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);

        try {
            equipos = equipoService.getEquipos();
            if (!equipos.isEmpty()) {
                CustomResponse<List<Equipo>> response = new CustomResponse<>(
                        1, "Equipos encontrados", equipos, links);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron equipos", equipos, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @Operation(summary = "Obtener un equipo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipo>> getEquipoById(
            @Parameter(description = "ID del equipo a buscar") @PathVariable Long id) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);

        try {
            Optional<Equipo> equipo = equipoService.getEquipoById(id);
            if (equipo.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Equipo encontrado", equipo.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @Operation(summary = "Crear un nuevo equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error al crear el equipo")
    })
    @PostMapping
    public ResponseEntity<CustomResponse<Equipo>> createEquipo(
            @Parameter(description = "Datos del equipo a crear") @RequestBody Equipo equipo) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);

        try {
            Equipo nuevoEquipo = equipoService.createEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Equipo creado exitosamente", nuevoEquipo, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear el equipo", null, links));
        }
    }

    @Operation(summary = "Actualizar un equipo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al actualizar el equipo")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipo>> updateEquipo(
            @Parameter(description = "ID del equipo a actualizar") @PathVariable Long id,
            @Parameter(description = "Nuevos datos del equipo") @RequestBody Equipo equipo) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);

        try {
            equipo.setId(id);
            if (equipoService.getEquipoById(id).isPresent()) {
                Equipo equipoActualizado = equipoService.updateEquipo(equipo);
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Equipo actualizado exitosamente", equipoActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar el equipo", null, links));
        }
    }

    @Operation(summary = "Eliminar un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar el equipo")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteEquipo(
            @Parameter(description = "ID del equipo a eliminar") @PathVariable Long id) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);

        try {
            if (equipoService.getEquipoById(id).isPresent()) {
                equipoService.deleteEquipo(id);
                return ResponseEntity.ok(new CustomResponse<>(
                        1, "Equipo eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar el equipo", null, links));
        }

    }
}
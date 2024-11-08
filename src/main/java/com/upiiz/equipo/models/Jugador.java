package com.upiiz.equipo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    @NotBlank
    private String nombre;

    @NotBlank
    private String edad;

    @NotBlank
    private String nacionalidad;

    @NotBlank
    private String posicion;

/**/
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

}

package com.upiiz.equipo.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String premio;
    @NotBlank
    private String inicio;
    @NotBlank
    private String fin;

    @OneToMany(mappedBy = "competencia", cascade = CascadeType.ALL)
    private List<EquipoCompetencia> equipoCompetencias;
}

package com.upiiz.equipo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipo_competencia")
public class EquipoCompetencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**/
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;
    /**/
    @ManyToOne
    @JoinColumn(name = "competencia_id")
    private Competencia competencia;
}

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
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(50)")
    @NotBlank
    private String nombre;

/**/
    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @OneToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;
/**/

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<Jugador> jugadores;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<EquipoCompetencia> equipoCompetencias;


}

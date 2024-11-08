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
public class Liga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    @NotBlank
    private String nombre;

    @NotBlank
    private String pais;

    @Column(columnDefinition = "VARCHAR(50)")
    @NotBlank
    private String presidente;



    @OneToMany(mappedBy = "liga", fetch = FetchType.LAZY)
    private List<Equipo> equipos;
}

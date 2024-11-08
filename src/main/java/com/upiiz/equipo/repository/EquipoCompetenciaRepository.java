package com.upiiz.equipo.repository;


import com.upiiz.equipo.models.EquipoCompetencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoCompetenciaRepository extends JpaRepository<EquipoCompetencia, Long> {
}

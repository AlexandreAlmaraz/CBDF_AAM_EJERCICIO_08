package com.upiiz.equipo.service;


import com.upiiz.equipo.models.EquipoCompetencia;
import com.upiiz.equipo.repository.EquipoCompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoCompetenciaService {

    @Autowired
    private EquipoCompetenciaRepository equipoCompetenciaRepository;

    public List<EquipoCompetencia> getAllEquipoCompetencias() {
        return equipoCompetenciaRepository.findAll();
    }

    public Optional<EquipoCompetencia> getEquipoCompetenciaById(Long id) {
        return equipoCompetenciaRepository.findById(id);
    }

    public EquipoCompetencia createEquipoCompetencia(EquipoCompetencia equipoCompetencia) {
        return equipoCompetenciaRepository.save(equipoCompetencia);
    }

    public EquipoCompetencia updateEquipoCompetencia(EquipoCompetencia equipoCompetencia) {
        return equipoCompetenciaRepository.save(equipoCompetencia);
    }

    public void deleteEquipoCompetencia(Long id) {
        equipoCompetenciaRepository.deleteById(id);
    }
}
package com.upiiz.equipo.service;


import com.upiiz.equipo.models.Competencia;
import com.upiiz.equipo.repository.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenciaService {

    @Autowired
    private CompetenciaRepository competenciaRepository;

    public List<Competencia> getCompetencias() {
        return competenciaRepository.findAll();
    }

    public Optional<Competencia> getCompetenciaById(Long id) {
        return competenciaRepository.findById(id);
    }

    public Competencia createCompetencia(Competencia competencia) {
        return competenciaRepository.save(competencia);
    }

    public Competencia updateCompetencia(Competencia competencia) {
        return competenciaRepository.save(competencia);
    }

    public void deleteCompetencia(Long id) {
        competenciaRepository.deleteById(id);
    }
}
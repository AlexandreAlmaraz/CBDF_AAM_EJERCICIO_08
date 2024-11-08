package com.upiiz.equipo.service;


import com.upiiz.equipo.models.Liga;
import com.upiiz.equipo.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

    @Autowired
    private LigaRepository ligaRepository;

    public List<Liga> getLigas() {
        return ligaRepository.findAll();
    }

    public Optional<Liga> getLigaById(Long id) {
        return ligaRepository.findById(id);
    }

    public Liga createLiga(Liga liga) {
        return ligaRepository.save(liga);
    }

    public Liga updateLiga(Liga liga) {
        return ligaRepository.save(liga);
    }

    public void deleteLiga(Long id) {
        ligaRepository.deleteById(id);
    }
}
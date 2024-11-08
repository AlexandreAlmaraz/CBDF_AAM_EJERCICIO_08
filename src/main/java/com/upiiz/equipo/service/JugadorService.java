package com.upiiz.equipo.service;


import com.upiiz.equipo.models.Jugador;
import com.upiiz.equipo.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> getJugadores() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> getJugadorById(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador createJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Jugador updateJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public void deleteJugador(Long id) {
        jugadorRepository.deleteById(id);
    }
}

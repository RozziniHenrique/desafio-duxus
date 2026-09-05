package br.com.duxusdesafio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    private final IntegranteRepository integranteRepository;

    public IntegranteController(IntegranteRepository integranteRepository) {
        this.integranteRepository = integranteRepository;
    }

    @PostMapping
    public ResponseEntity<Integrante> cadastrarIntegrante(@RequestBody Integrante integrante) {
        Integrante novoIntegrante = integranteRepository.save(integrante);
        return ResponseEntity.status(201).body(novoIntegrante);
    }
    
}

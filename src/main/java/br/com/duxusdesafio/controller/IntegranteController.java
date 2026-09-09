package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

  private final IntegranteRepository integranteRepository;

  public IntegranteController(IntegranteRepository integranteRepository) {
    this.integranteRepository = integranteRepository;
  }

  public ResponseEntity<Integrante> cadastrarIntegrante(
    @Valid @RequestBody Integrante integrante
  ) {
    Integrante novoIntegrante = integranteRepository.save(integrante);
    return ResponseEntity.status(201).body(novoIntegrante);
  }
}

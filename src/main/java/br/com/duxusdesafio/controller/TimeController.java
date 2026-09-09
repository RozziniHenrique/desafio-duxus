package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.TimeCadastroRequest;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.ComposicaoTimeRepository;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.service.ApiService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class TimeController {

  private final TimeRepository timeRepository;
  private final ComposicaoTimeRepository composicaoTimeRepository;
  private final IntegranteRepository integranteRepository;
  private final ApiService apiService;

  public TimeController(
    TimeRepository timeRepository,
    ComposicaoTimeRepository composicaoTimeRepository,
    IntegranteRepository integranteRepository,
    ApiService apiService
  ) {
    this.timeRepository = timeRepository;
    this.composicaoTimeRepository = composicaoTimeRepository;
    this.integranteRepository = integranteRepository;
    this.apiService = apiService;
  }

  @PostMapping
  public ResponseEntity<Time> cadastrarTime(
    @RequestBody TimeCadastroRequest time
  ) {
    if (
      time.getNomeDoClube() == null ||
      time.getNomeDoClube().trim().isEmpty() ||
      time.getData() == null ||
      time.getIntegrantesIds() == null ||
      time.getIntegrantesIds().isEmpty()
    ) {
      return ResponseEntity.badRequest().build();
    }

    Set<Long> idsUnicos = new HashSet<>(time.getIntegrantesIds());

    if (idsUnicos.size() != time.getIntegrantesIds().size()) {
      return ResponseEntity.badRequest().build();
    }

    for (Long integranteId : time.getIntegrantesIds()) {
      if (
        integranteId == null || !integranteRepository.existsById(integranteId)
      ) {
        return ResponseEntity.badRequest().build();
      }
    }

    Time novoTime = new Time();
    novoTime.setNomeDoClube(time.getNomeDoClube());
    novoTime.setData(time.getData());
    timeRepository.save(novoTime);

    for (Long integranteId : time.getIntegrantesIds()) {
      Integrante integrante = integranteRepository
        .findById(integranteId)
        .orElse(null);

      ComposicaoTime composicaoTime = new ComposicaoTime();
      composicaoTime.setTime(novoTime);
      composicaoTime.setIntegrante(integrante);

      composicaoTimeRepository.save(composicaoTime);
    }

    return ResponseEntity.status(201).body(novoTime);
  }

  @GetMapping("/data/{data}")
  public ResponseEntity<Time> buscarTimePorData(
    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
  ) {
    List<Time> times = timeRepository.findAll();

    Time timeEncontrado = apiService.timeDaData(data, times);

    if (timeEncontrado != null) {
      return ResponseEntity.ok(timeEncontrado);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/integrante-mais-usado")
  public ResponseEntity<Integrante> buscarIntegranteMaisUsado(
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataInicial,
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataFinal
  ) {
    List<Time> times = timeRepository.findAll();

    Integrante integranteMaisUsado = apiService.integranteMaisUsado(
      dataInicial,
      dataFinal,
      times
    );

    if (integranteMaisUsado != null) {
      return ResponseEntity.ok(integranteMaisUsado);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/integrantes-do-time-mais-recorrente")
  public ResponseEntity<List<String>> buscarIntegrantesDoTimeMaisRecorrente(
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataInicial,
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataFinal
  ) {
    List<Time> times = timeRepository.findAll();

    List<String> integrante = apiService.integrantesDoTimeMaisRecorrente(
      dataInicial,
      dataFinal,
      times
    );

    if (integrante != null && !integrante.isEmpty()) {
      return ResponseEntity.ok(integrante);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/funcao-mais-recorrente")
  public ResponseEntity<String> buscarFuncaoMaisRecorrente(
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataInicial,
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataFinal
  ) {
    List<Time> times = timeRepository.findAll();

    String funcaoMaisRecorrente = apiService.funcaoMaisRecorrente(
      dataInicial,
      dataFinal,
      times
    );

    if (funcaoMaisRecorrente != null) {
      return ResponseEntity.ok(funcaoMaisRecorrente);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/clube-mais-recorrente")
  public ResponseEntity<String> buscarClubeMaisRecorrente(
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataInicial,
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataFinal
  ) {
    List<Time> times = timeRepository.findAll();
    String clubeMaisRecorrente = apiService.clubeMaisRecorrente(
      dataInicial,
      dataFinal,
      times
    );

    if (clubeMaisRecorrente != null) {
      return ResponseEntity.ok(clubeMaisRecorrente);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/contagem-de-clubes-no-periodo")
  public ResponseEntity<Map<String, Long>> buscarContagemDeClubesNoPeriodo(
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataInicial,
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataFinal
  ) {
    List<Time> times = timeRepository.findAll();

    Map<String, Long> contagem = apiService.contagemDeClubesNoPeriodo(
      dataInicial,
      dataFinal,
      times
    );

    if (contagem != null) {
      return ResponseEntity.ok(contagem);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/contagem-por-funcao")
  public ResponseEntity<Map<String, Long>> buscarContagemPorFuncao(
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataInicial,
    @RequestParam(required = false) @DateTimeFormat(
      iso = DateTimeFormat.ISO.DATE
    ) LocalDate dataFinal
  ) {
    List<Time> times = timeRepository.findAll();

    Map<String, Long> contagem = apiService.contagemPorFuncao(
      dataInicial,
      dataFinal,
      times
    );

    if (contagem != null) {
      return ResponseEntity.ok(contagem);
    }

    return ResponseEntity.notFound().build();
  }
}

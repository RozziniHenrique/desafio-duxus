package br.com.duxusdesafio.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dto.TimeCadastroRequest;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.ComposicaoTimeRepository;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.service.ApiService;


@RestController 
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;
    private final ComposicaoTimeRepository composicaoTimeRepository;
    private final IntegranteRepository integranteRepository;
    private final ApiService apiService;

    public TimeController(TimeRepository timeRepository, ComposicaoTimeRepository composicaoTimeRepository, IntegranteRepository integranteRepository, ApiService apiService) {
        this.timeRepository = timeRepository;
        this.composicaoTimeRepository = composicaoTimeRepository;
        this.integranteRepository = integranteRepository;
        this.apiService = apiService;
    }

    @PostMapping
    public ResponseEntity<Time> cadastrarTime(@RequestBody TimeCadastroRequest time){

        if (time.getIntegrantesIds() == null || time.getIntegrantesIds().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Set<Long> idsUnicos = new HashSet<>(time.getIntegrantesIds());

        if(idsUnicos.size() != time.getIntegrantesIds().size()) {
            return ResponseEntity.badRequest().build();
        }

        for (Long integranteId : time.getIntegrantesIds()){
            if (!integranteRepository.existsById(integranteId)) {
                return ResponseEntity.badRequest().build();
            }
        }

        Time novoTime = new Time();
        novoTime.setNomeDoClube(time.getNomeDoClube());
        novoTime.setData(time.getData());
        timeRepository.save(novoTime);

        for (Long integranteId : time.getIntegrantesIds()){
            Integrante integrante = integranteRepository.findById(integranteId).orElse(null);

            ComposicaoTime composicaoTime = new ComposicaoTime();
            composicaoTime.setTime(novoTime);
            composicaoTime.setIntegrante(integrante);

            composicaoTimeRepository.save(composicaoTime);
        }

    return ResponseEntity.status(201).body(novoTime);
    }

    @GetMapping("/data/{data}")
        public ResponseEntity<Time> buscarTimePorData(
                @PathVariable
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                LocalDate data) {

            List<Time> times = timeRepository.findAll();

            Time timeEncontrado = apiService.timeDaData(data, times);

            if (timeEncontrado != null) {
                return ResponseEntity.ok(timeEncontrado);
            }

            return ResponseEntity.notFound().build();
        }
    }

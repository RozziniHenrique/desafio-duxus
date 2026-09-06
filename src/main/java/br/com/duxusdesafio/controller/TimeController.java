package br.com.duxusdesafio.controller;

import org.springframework.http.ResponseEntity;
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

@RestController 
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;
    private final ComposicaoTimeRepository composicaoTimeRepository;
    private final IntegranteRepository integranteRepository;

    public TimeController(TimeRepository timeRepository, ComposicaoTimeRepository composicaoTimeRepository, IntegranteRepository integranteRepository) {
        this.timeRepository = timeRepository;
        this.composicaoTimeRepository = composicaoTimeRepository;
        this.integranteRepository = integranteRepository;
    }

    @PostMapping
    public ResponseEntity<Time> cadastrarTime(@RequestBody TimeCadastroRequest time){

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
}

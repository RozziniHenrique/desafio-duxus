package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * OBS ao candidato: PREFERENCIALMENTE, NÃO ALTERE AS ASSINATURAS DOS MÉTODOS!
 * Trabalhe com a proposta pura.
 *
 * @author carlosau
 */
@Service
public class ApiService {

    /**
     * Vai retornar um Time, com a composição do time daquela data
     */
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
        for (Time time : todosOsTimes) {
            if (time.getData().equals(data)) {
                return time;
            }
        }
        return null;
    }

    /**
     * Vai retornar o integrante que estiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        HashMap<Integrante, Integer> contagemIntegrantes = new HashMap<>();

        for (Time time : todosOsTimes) {

            boolean dentroDoPeriodo = false;

                //dataInicial e dataFinal null = todas as datas são consideradas
                if (dataInicial == null && dataFinal == null) {
                    dentroDoPeriodo = true;
                }

                //dataFinal null = todas as datas a partir da dataInicial são consideradas
                else if (dataFinal == null && !time.getData().isBefore(dataInicial)) {
                    dentroDoPeriodo = true;
                }

                //dataInicial null = todas as datas até a dataFinal são consideradas
                else if (dataInicial == null && !time.getData().isAfter(dataFinal)) {
                    dentroDoPeriodo = true;
                }

                //dataInicial e dataFinal não null = todas as datas entre a dataInicial e a dataFinal são consideradas
                else if (!time.getData().isBefore(dataInicial) && !time.getData().isAfter(dataFinal)) {
                    dentroDoPeriodo = true;
                }

            if(dentroDoPeriodo) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    Integrante integrante = composicao.getIntegrante();
                    contagemIntegrantes.put(integrante, contagemIntegrantes.getOrDefault(integrante, 0) + 1);
                }
            }
        }

        Integrante integranteMaisUsado = null;
        int maiorContagem = 0;
        for (Map.Entry<Integrante, Integer> entry : contagemIntegrantes.entrySet()) {
            if (entry.getValue() > maiorContagem) {
                maiorContagem = entry.getValue();
                integranteMaisUsado = entry.getKey();
            }
        }
        return integranteMaisUsado;
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais recorrente dentro do período.
     * OBS: Time é o clube + composição em determinada data
     */
    public List<String> integrantesDoTimeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){

    HashMap<String, HashMap<Set<String>, Integer>> contagemTimes = new HashMap<>();

    for (Time time : todosOsTimes) {

        boolean dentroDoPeriodo = false;

        if (dataInicial == null && dataFinal == null) {
            dentroDoPeriodo = true;
        } else if (dataFinal == null && !time.getData().isBefore(dataInicial)) {
            dentroDoPeriodo = true;
        } else if (dataInicial == null && !time.getData().isAfter(dataFinal)) {
            dentroDoPeriodo = true;
        } else if (!time.getData().isBefore(dataInicial)
                && !time.getData().isAfter(dataFinal)) {
            dentroDoPeriodo = true;
        }

        if (dentroDoPeriodo) {

            Set<String> integrantes = new HashSet<>();

            for (ComposicaoTime composicao : time.getComposicaoTime()) {
                integrantes.add(composicao.getIntegrante().getNome());
            }

            String clube = time.getNomeDoClube();

            if (!contagemTimes.containsKey(clube)) {
                contagemTimes.put(clube, new HashMap<>());
            }

            HashMap<Set<String>, Integer> composicoesDoClube =
                    contagemTimes.get(clube);

            composicoesDoClube.put(
                integrantes,
                composicoesDoClube.getOrDefault(integrantes, 0) + 1
            );
        }
    }
    Set <String> composicaoMaisRecorrente = null;
    int maiorContagem = 0;
    for (Map.Entry<String, HashMap<Set<String>, Integer>> entry : contagemTimes.entrySet()) {
        for (Map.Entry<Set<String>, Integer> composicaoEntry : entry.getValue().entrySet()) {
            if (composicaoEntry.getValue() > maiorContagem) {
                maiorContagem = composicaoEntry.getValue();
                composicaoMaisRecorrente = composicaoEntry.getKey();
            }
        }
    }

    if (composicaoMaisRecorrente == null) {
        return null;
    }
    return new ArrayList<>(composicaoMaisRecorrente);
}

    /**
     * Vai retornar a função mais recorrente nos times dentro do período
     */
    public String funcaoMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o nome do Clube mais comum dentro do período
     */
    public String clubeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // TODO Implementar método seguindo as instruções!
        return null;
    }


    /**
     * Vai retornar o número (quantidade) de aparições de cada Clube participante no período
     */
    public Map<String, Long> contagemDeClubesNoPeriodo(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período.
     * Dica - pense sobre repetições!
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

}

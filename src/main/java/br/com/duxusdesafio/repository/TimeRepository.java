package br.com.duxusdesafio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.duxusdesafio.model.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {

    List<Time> findAllByData(LocalDate data);

}
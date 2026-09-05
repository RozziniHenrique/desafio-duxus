package br.com.duxusdesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.duxusdesafio.model.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {

}
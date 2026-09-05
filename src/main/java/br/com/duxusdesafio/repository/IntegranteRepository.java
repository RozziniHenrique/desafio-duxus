package br.com.duxusdesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.duxusdesafio.model.Integrante;

public interface IntegranteRepository extends JpaRepository<Integrante, Long> {
    
}

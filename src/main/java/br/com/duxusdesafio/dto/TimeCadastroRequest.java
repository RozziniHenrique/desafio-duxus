package br.com.duxusdesafio.dto;

import java.time.LocalDate;
import java.util.List;

public class TimeCadastroRequest {

  private String nomeDoClube;

  private LocalDate data;

  private List<Long> integrantesIds;

  //Getters and Setters
  public String getNomeDoClube() {
    return nomeDoClube;
  }

  public void setNomeDoClube(String nomeDoClube) {
    this.nomeDoClube = nomeDoClube;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public List<Long> getIntegrantesIds() {
    return integrantesIds;
  }

  public void setIntegrantesIds(List<Long> integrantesIds) {
    this.integrantesIds = integrantesIds;
  }
}

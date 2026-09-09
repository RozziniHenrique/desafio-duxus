package br.com.duxusdesafio.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> tratarErroDeValidacao(
    MethodArgumentNotValidException exception
  ) {
    Map<String, Object> erro = new HashMap<>();

    erro.put("status", 400);
    erro.put("erro", "Requisição inválida");

    return ResponseEntity.badRequest().body(erro);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> tratarErroGenerico(
    Exception exception
  ) {
    Map<String, Object> erro = new HashMap<>();

    erro.put("status", 500);
    erro.put("erro", "Erro interno do servidor");

    return ResponseEntity.status(500).body(erro);
  }
}

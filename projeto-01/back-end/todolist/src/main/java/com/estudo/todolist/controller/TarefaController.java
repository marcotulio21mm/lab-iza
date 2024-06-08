package com.estudo.todolist.controller;

import com.estudo.todolist.exception.DataInvalidaException;
import com.estudo.todolist.exception.TarefaNotFoundException;
import com.estudo.todolist.exception.TituloInvalidoException;
import com.estudo.todolist.entity.*;
import com.estudo.todolist.response.TarefaDTO;
import com.estudo.todolist.response.TarefaDTOData;
import com.estudo.todolist.response.TarefaDTOLivre;
import com.estudo.todolist.response.TarefaDTOPrazo;
import com.estudo.todolist.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Object> criarTarefa(@Valid @RequestParam String titulo) {
        try {
            return ResponseEntity.ok(tarefaService.criarTarefa(titulo));
        }catch (TituloInvalidoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/v2/data")
    public ResponseEntity<Object> criarTarefa(@RequestBody TarefaDTOData request) {
        System.out.println(request);
        try {
            return ResponseEntity.ok(tarefaService.criarTarefa(request));
        }catch (TituloInvalidoException | DataInvalidaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/v2/prazo")
    public ResponseEntity<Object> criarTarefa(@RequestBody TarefaDTOPrazo request) {
        try {
            return ResponseEntity.ok(tarefaService.criarTarefa(request));
        }catch (TituloInvalidoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/v2/livre")
    public ResponseEntity<Object> criarTarefa(@RequestBody TarefaDTOLivre request) {
        try {
            return ResponseEntity.ok(tarefaService.criarTarefa(request));
        }catch (TituloInvalidoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("/{id}/status/concluir")
    public ResponseEntity<Tarefa> concluirTarefa(@PathVariable long id) {
        return ResponseEntity.ok(tarefaService.mudarStatus(id, true));
    }
    @PostMapping("/{id}/status/desconcluir")
    public ResponseEntity<Tarefa> desconcluirTarefa(@PathVariable long id) {
        return ResponseEntity.ok(tarefaService.mudarStatus(id, false));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTarefa(@PathVariable long id) {
        try {
            tarefaService.excluirTarefa(id);
            return ResponseEntity.ok("Tarefa com ID " + id + " excluída com sucesso.");
        } catch (TarefaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping
    public List<TarefaDTO> retornarTodasTarefas() {
        return tarefaService.retornarTodasTarefas();
    }
    @GetMapping("/v2/data")
    public List<TarefaDTOData> retornarTarefasData() {
        return tarefaService.retornarTarefasData();
    }
    @GetMapping("/v2/prazo")
    public List<TarefaDTOPrazo> retornarTarefasPrazo() {
        return tarefaService.retornarTarefasPrazo();
    }
    @GetMapping("/v2/livre")
    public List<TarefaDTOLivre> retornarTarefasLivre() {
        return tarefaService.retornarTarefasLivre();
    }
}

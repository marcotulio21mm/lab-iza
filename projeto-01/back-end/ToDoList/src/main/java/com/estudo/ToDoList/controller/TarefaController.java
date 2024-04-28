package com.estudo.ToDoList.controller;

import com.estudo.ToDoList.entity.*;
import com.estudo.ToDoList.response.TarefaDTO;
import com.estudo.ToDoList.response.TarefaDTOData;
import com.estudo.ToDoList.response.TarefaDTOLivre;
import com.estudo.ToDoList.response.TarefaDTOPrazo;
import com.estudo.ToDoList.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@Valid @RequestParam String titulo) {
        return tarefaService.criarTarefa(titulo);
    }
    @PostMapping("/v2/data")
    public Tarefa criarTarefa(@RequestBody TarefaDTOData request) {
        return tarefaService.criarTarefa(request);
    }
    @PostMapping("/v2/prazo")
    public Tarefa criarTarefa(@RequestBody TarefaDTOPrazo request) {
        return tarefaService.criarTarefa(request);
    }
    @PostMapping("/v2/livre")
    public Tarefa criarTarefa(@RequestBody TarefaDTOLivre request) {
        return tarefaService.criarTarefa(request);
    }
    @PostMapping("/{id}/status/concluir")
    public Tarefa concluirTarefa(@PathVariable long id) {
        return tarefaService.mudarStatus(id, true);
    }
    @PostMapping("/{id}/status/desconcluir")
    public Tarefa desconcluirTarefa(@PathVariable long id) {
        return tarefaService.mudarStatus(id, false);
    }
    @DeleteMapping("/{id}")
    public void excluirTarefa(@PathVariable long id) {
        tarefaService.excluirTarefa(id);
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

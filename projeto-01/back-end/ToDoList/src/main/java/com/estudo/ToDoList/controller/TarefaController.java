package com.estudo.ToDoList.controller;

import com.estudo.ToDoList.entity.*;
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
    @PostMapping("/v2")
    public Tarefa criarTarefaV2(@RequestBody TarefaRequest request) {
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
    public List<Tarefa> retornarTodasTarefas() {
        return tarefaService.retornarTodasTarefas();
    }
    @GetMapping("/v2/data")
    public List<TarefaData> retornarTarefasData() {
        return tarefaService.retornarTarefasData();
    }

    @GetMapping("/v2/prazo")
    public List<TarefaPrazo> retornarTarefasPrazo() {
        return tarefaService.retornarTarefasPrazo();
    }

    @GetMapping("/v2/livre")
    public List<TarefaLivre> retornarTarefasLivre() {
        return tarefaService.retornarTarefasLivre();
    }
}

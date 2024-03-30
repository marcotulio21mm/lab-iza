package com.estudo.ToDoList.controller;

import com.estudo.ToDoList.entity.Tarefa;
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
}

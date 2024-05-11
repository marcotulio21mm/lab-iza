package com.estudo.todolist.repository;

import com.estudo.todolist.entity.Tarefa;
import com.estudo.todolist.entity.TarefaData;
import com.estudo.todolist.entity.TarefaLivre;
import com.estudo.todolist.enums.TipoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT t FROM Tarefa t WHERE t.tarefa = ?1 ORDER BY t.status DESC, t.prioridade DESC")
    List<TarefaData> findByTarefaData(TipoTarefa data);
    @Query("SELECT t FROM Tarefa AS t WHERE t.tarefa = ?1 ORDER BY t.status DESC, t.prioridade DESC")
    List<TarefaData> findByTarefaPrazo(TipoTarefa prazo);
    @Query("SELECT t FROM Tarefa t WHERE t.tarefa = ?1 ORDER BY t.status DESC, t.prioridade DESC")
    List<TarefaLivre> findByLivre(TipoTarefa livre);
}


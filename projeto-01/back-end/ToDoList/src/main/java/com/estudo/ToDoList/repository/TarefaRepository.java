package com.estudo.ToDoList.repository;

import com.estudo.ToDoList.entity.Tarefa;
import com.estudo.ToDoList.entity.TarefaData;
import com.estudo.ToDoList.entity.TarefaLivre;
import com.estudo.ToDoList.entity.TarefaPrazo;
import com.estudo.ToDoList.enums.TipoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT t FROM Tarefa t WHERE t.tipo = ?1 ORDER BY t.status DESC, t.prioridade DESC, t.dataPrevista ASC")
    List<TarefaData> findByTarefaData(TipoTarefa data);
    @Query("SELECT t FROM Tarefa AS t WHERE t.tipo = ?1 ORDER BY t.status DESC, t.prioridade DESC, t.prazoDias ASC")
    List<TarefaPrazo> findByTarefaPrazo(TipoTarefa prazo);
    @Query("SELECT t FROM Tarefa t WHERE t.tipo = ?1 ORDER BY t.status DESC, t.prioridade DESC")
    List<TarefaLivre> findByLivre(TipoTarefa livre);
}


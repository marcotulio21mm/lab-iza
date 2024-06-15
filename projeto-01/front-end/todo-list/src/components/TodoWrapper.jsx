import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TodoForm from './TodoForm';
import TodoList from './TodoList';
import DialogComponent from './DialogComponent';

const TodoWrapper = () => {
  const [rows, setRows] = useState([]);
  const [open, setOpen] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);
  const [confirmDelete, setConfirmDelete] = useState(false);

  useEffect(() => {
    axios.get('http://localhost:8080/tarefas')
      .then(response => {
        setRows(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the data!', error);
      });
  }, []);

  const handleCheckboxChange = (task) => {
    setSelectedTask(task);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedTask(null);
    setConfirmDelete(false);
  };

  const handleConfirm = () => {
    if (selectedTask) {
      const endpoint = selectedTask.status
        ? `http://localhost:8080/tarefas/${selectedTask.id}/status/desconcluir`
        : `http://localhost:8080/tarefas/${selectedTask.id}/status/concluir`;

      axios.post(endpoint)
        .then(response => {
          setRows(rows.map(row => {
            if (row.id === selectedTask.id) {
              return { ...row, completed: !row.completed };
            }
            return row;
          }));
          handleClose();
          window.location.reload()
        })
        .catch(error => {
          console.error('Erro ao mudar status da tarefa!', error);
        });
    }
  };

  const handleDelete = () => {
    if (selectedTask) {
      axios.delete(`http://localhost:8080/tarefas/${selectedTask.id}`)
        .then(response => {
          setRows(rows.filter(row => row.id !== selectedTask.id));
          handleClose();
        })
        .catch(error => {
          console.error('Erro ao excluir tarefa!', error);
        });
    }
  };

  const addTodo = (newTask) => {
    let endpoint = 'http://localhost:8080/tarefas/v2/livre';
    const task = { titulo: newTask.title, tarefa: newTask.age, prioridade: newTask.priority, status: false };

    if (newTask.age === 'DATA') {
      endpoint = 'http://localhost:8080/tarefas/v2/data';
      task.dataEsperada = newTask.dataEsperada;
    } else if (newTask.age === 'PRAZO') {
      endpoint = 'http://localhost:8080/tarefas/v2/prazo';
      task.diasPrevisto = newTask.diasPrevisto;
    }

    axios.post(endpoint, task)
      .then(response => {
        setRows([...rows, response.data]);
        window.location.reload()
      })
      .catch(error => {
        console.error('Erro ao adicionar tarefa!', error);
      });
  };

  return (
    <>
      <TodoForm addTodo={addTodo} />
      <TodoList tasks={rows} onCheckboxChange={handleCheckboxChange} onDelete={(task) => { setSelectedTask(task); setConfirmDelete(true); setOpen(true); }} />
      <DialogComponent
        open={open}
        onClose={handleClose}
        onConfirm={confirmDelete ? handleDelete : handleConfirm}
        task={selectedTask}
        confirmDelete={confirmDelete}
      />
    </>
  );
};

export default TodoWrapper;

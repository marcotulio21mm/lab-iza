import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Checkbox, IconButton } from '@mui/material';
import { Icon } from '@iconify/react';
import dayjs from 'dayjs';

const TodoList = ({ tasks, onCheckboxChange, onDelete }) => {
  const calculateDeliveryDate = (task) => {
    if (task.tarefa === 'PRAZO') {
      return dayjs().add(task.diasPrevisto, 'day').format('YYYY-MM-DD');
    } else if (task.tarefa === 'DATA') {
      return task.dataEsperada;
    } else {
      return 'N/A';
    }
  };

  return (
    <TableContainer component={Paper} sx={{ maxWidth: "80%", marginLeft: "auto", marginRight: "auto", marginTop: "2rem" }}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="left"></TableCell>
            <TableCell align="left">Título</TableCell>
            <TableCell align="left">Prioridade</TableCell>
            <TableCell align="left">Status</TableCell>
            <TableCell align="left">Tipo</TableCell>
            <TableCell align="left">Data de Entrega</TableCell>
            <TableCell align="left">Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {tasks.map((task) => (
            <TableRow key={task.id}>
              <TableCell align="left">
                <Checkbox
                  checked={task.status === true}
                  onChange={() => onCheckboxChange(task)}
                />
              </TableCell>
              <TableCell align="left">{task.titulo}</TableCell>
              <TableCell align="left">{task.prioridade}</TableCell>
              <TableCell align="left">{task.status ? 'Concluída' : 'Pendente'}</TableCell>
              <TableCell align="left">{task.tarefa}</TableCell>
              <TableCell align="left">{calculateDeliveryDate(task)}</TableCell>
              <TableCell align="left">
                <IconButton onClick={() => onDelete(task)}>
                  <Icon icon={"mdi:trash"} />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default TodoList;

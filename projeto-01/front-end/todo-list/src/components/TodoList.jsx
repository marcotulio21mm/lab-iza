import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableRow, Paper, Checkbox, IconButton } from '@mui/material';
import { Icon } from '@iconify/react';
import dayjs from 'dayjs';

const TodoList = ({ tasks, onCheckboxChange, onDelete }) => {
  const calculateDeliveryMessage = (task) => {
    const today = dayjs();
    if (task.tarefa === 'PRAZO') {
      const dueDate = dayjs().add(task.diasPrevisto, 'day');
      if (dueDate.isBefore(today, 'day')) {
        return 'Prazo para conclusão vencido';
      } else {
        return `Prazo para conclusão: ${task.diasPrevisto} dias`;
      }
    } else if (task.tarefa === 'DATA') {
      const expectedDate = dayjs(task.dataEsperada);
      if (expectedDate.isBefore(today, 'day')) {
        return 'Data de conclusão vencida';
      } else {
        return `Data para conclusão: ${expectedDate.format('DD/MM/YYYY')}`;
      }
    } else {
      return ''; // Retorna uma string vazia para outros tipos de tarefa
    }
  };

  return (
    <TableContainer component={Paper} sx={{ maxWidth: "80%", marginLeft: "auto", marginRight: "auto", marginTop: "2rem" }}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableBody>
          {tasks.map((task) => (
            <TableRow key={task.id}>
              <TableCell align="left" sx={{ padding: '8px', verticalAlign: 'top', width: "30%" }}>
                {(task.status || calculateDeliveryMessage(task) !== '') && ( // Mostra a mensagem se a tarefa estiver concluída ou se houver mensagem de prazo/data
                  <div style={{ marginBottom: '8px' }}>{calculateDeliveryMessage(task)}</div>
                )}
                <Checkbox
                  checked={task.status === true}
                  onChange={() => onCheckboxChange(task)}
                  sx={{
                    '&.Mui-checked': {
                      color: '#F7A17C',
                    },
                  }}
                />
              </TableCell>
              <TableCell align="left">
                <div>{task.titulo}</div>
              </TableCell>
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

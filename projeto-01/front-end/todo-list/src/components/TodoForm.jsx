import React, { useState } from 'react';
import { TextField, FormControl, InputLabel, MenuItem, Select, Grid, Button } from '@mui/material';

const TodoForm = ({ addTodo }) => {
  const [age, setAge] = useState('LIVRE');
  const [title, setTitle] = useState('');
  const [priority, setPriority] = useState('');
  const [dataEsperada, setDataEsperada] = useState('');
  const [diasPrevisto, setDiasPrevisto] = useState('');

  const handleChange = (event) => {
    setAge(event.target.value);
  };

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handlePriorityChange = (event) => {
    setPriority(event.target.value);
  };

  const handleDataEsperadaChange = (event) => {
    setDataEsperada(event.target.value);
  };

  const handleDiasPrevistoChange = (event) => {
    setDiasPrevisto(event.target.value);
  };

  const handleAddTarefa = () => {
    addTodo({ title, age, priority, dataEsperada, diasPrevisto });
    setTitle('');
    setAge('LIVRE');
    setPriority('');
    setDataEsperada('');
    setDiasPrevisto('');
  };

  return (
    <Grid container spacing={1} sx={{ width: "80%", margin: "auto", marginTop: "20px" }}>
      <Grid item xs={3} sx={{ marginLeft: "2rem" }}>
        <TextField
          id="outlined-basic"
          label="Título"
          variant="outlined"
          fullWidth
          size='small'
          value={title}
          onChange={handleTitleChange}
        />
      </Grid>

      <Grid item xs={3}>
        <FormControl fullWidth>
          <InputLabel id="priority-select-label">Prioridade</InputLabel>
          <Select
            labelId="priority-select-label"
            id="priority-select"
            value={priority}
            size="small"
            label="Prioridade"
            onChange={handlePriorityChange}
          >
            <MenuItem value="BAIXA">Baixa</MenuItem>
            <MenuItem value="MEDIA">Média</MenuItem>
            <MenuItem value="ALTA">Alta</MenuItem>
          </Select>
        </FormControl>
      </Grid>

      <Grid item xs={3}>
        <FormControl fullWidth>
          <InputLabel id="demo-simple-select-label">Data tarefa</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={age}
            size="small"
            defaultValue='LIVRE'
            label="Data tarefa"
            onChange={handleChange}
          >
            <MenuItem value="LIVRE">Livre</MenuItem>
            <MenuItem value="DATA">Data</MenuItem>
            <MenuItem value="PRAZO">Prazo em dias</MenuItem>
          </Select>
        </FormControl>
      </Grid>
      <Grid item xs={2} fullWidth sx={{ textAlign: "center" }}>
        <Button sx={{background: "#F7A17C"}} variant="contained" onClick={handleAddTarefa}>+</Button>
      </Grid>

      {age === 'DATA' && (
        <Grid item xs={4}>
          <TextField
            id="data-esperada"
            label="Data Esperada"
            type="date"
            size='small'
            fullWidth
            InputLabelProps={{
              shrink: true,
            }}
            value={dataEsperada}
            onChange={handleDataEsperadaChange}
          />
        </Grid>
      )}

      {age === 'PRAZO' && (
        <Grid item xs={4}>
          <TextField
            id="dias-previsto"
            label="Dias Previstos"
            fullWidth
            size='small'
            type="number"
            value={diasPrevisto}
            onChange={handleDiasPrevistoChange}
          />
        </Grid>
      )}
    </Grid>
  );
};

export default TodoForm;

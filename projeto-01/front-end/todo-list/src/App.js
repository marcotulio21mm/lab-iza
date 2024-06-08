import './App.css';
import {
    Button,
    FormControl,
    Grid,
    InputLabel,
    MenuItem,
    Paper,
    TextField,
    Container,
    Checkbox,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    IconButton
} from "@mui/material";
import Select from '@mui/material/Select';
import React, { useEffect, useState } from "react";
import axios from "axios";
import { Icon } from "@iconify/react/dist/iconify.js";

function App() {
    const [age, setAge] = useState('LIVRE');
    const [title, setTitle] = useState('');
    const [priority, setPriority] = useState('');
    const [dataEsperada, setDataEsperada] = useState('');
    const [diasPrevisto, setDiasPrevisto] = useState('');
    const [rows, setRows] = useState([]);
    const [open, setOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);
    const [confirmDelete, setConfirmDelete] = useState(false);

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

    const handleAddTask = () => {
        let endpoint = 'http://localhost:8080/tarefas/v2/livre';
        const newTask = { titulo: title, tarefa: age, prioridade: priority, status: false };

        if (age === 'DATA') {
            endpoint = 'http://localhost:8080/tarefas/v2/data';
            newTask.dataEsperada = dataEsperada;
        } else if (age === 'PRAZO') {
            endpoint = 'http://localhost:8080/tarefas/v2/prazo';
            newTask.diasPrevisto = diasPrevisto;
        }

        axios.post(endpoint, newTask)
            .then(response => {
                setRows([...rows, response.data]);
                setTitle('');
                setAge('');
                setPriority('');
                setDataEsperada('');
                setDiasPrevisto('');
            })
            .catch(error => {
                console.error('Erro ao adicionar tarefa!', error);
            });
    };

    return (
        <div className="App" sx={{display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}}>
            <h1>Lista de Tarefas</h1>
            <Grid container md={12} spacing={1} sx={{  width: "80%", display: "flex", marginLeft: "auto", marginRight: "auto", marginTop: "auto" }}>
                <Grid item xs={3} sx={{marginLeft: "2rem"}}>
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
                <Grid item xs={2} fullWidth sx={{textAlign: "center"}}>
                    <Button variant="contained" onClick={handleAddTask}>+</Button>
                </Grid>

                {age === 'DATA' && (
                    <Grid item xs={4} >
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
                            label="Dias Previsto"
                            fullWidth
                            size='small'
                            type="number"
                            value={diasPrevisto}
                            onChange={handleDiasPrevistoChange}
                        />
                    </Grid>
                )}


            </Grid>
            <Container sx={{ marginTop: "2rem" }}>
                <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="left"></TableCell>
                                <TableCell align="left">Título</TableCell>
                                <TableCell align="left">Prioridade</TableCell>
                                <TableCell align="left">Status</TableCell>
                                <TableCell align="left">Tipo</TableCell>
                                <TableCell align="left">Ações</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.map((row) => (
                                <TableRow key={row.id}>
                                    <TableCell align="left">
                                        <Checkbox
                                            checked={row.completed || false}
                                            onChange={() => handleCheckboxChange(row)}
                                        />
                                    </TableCell>
                                    <TableCell align="left">{row.titulo}</TableCell>
                                    <TableCell align="left">{row.prioridade}</TableCell>
                                    <TableCell align="left">{row.status ? 'Concluída' : 'Pendente'}</TableCell>
                                    <TableCell align="left">{row.tipo}</TableCell>
                                    <TableCell align="left">
                                        <IconButton onClick={() => {
                                            setSelectedTask(row);
                                            setConfirmDelete(true);
                                            setOpen(true);
                                        }}>
                                            <Icon icon={"mdi:trash"} />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Container>

            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {confirmDelete ? "Confirmar Exclusão de Tarefa" : "Confirmar Conclusão de Tarefa"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {confirmDelete
                            ? `Você tem certeza que deseja excluir a tarefa "${selectedTask?.titulo}"?`
                            : `Você tem certeza que deseja marcar a tarefa "${selectedTask?.titulo}" como ${selectedTask?.status ? 'desconcluída' : 'concluída'}?`}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancelar</Button>
                    <Button onClick={confirmDelete ? handleDelete : handleConfirm} autoFocus>
                        Confirmar
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default App;

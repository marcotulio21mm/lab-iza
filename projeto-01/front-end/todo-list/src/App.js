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
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import React, { useEffect, useState } from "react";
import axios from "axios";
import { Icon } from "@iconify/react/dist/iconify.js";

function App() {
    const [age, setAge] = useState('');
    const [rows, setRows] = useState([]);
    const [open, setOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);
    const [confirmDelete, setConfirmDelete] = useState(false);

    const handleChange = (event) => {
        setAge(event.target.value);
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
            if(selectedTask.status){
                axios.post(`http://localhost:8080/tarefas/${selectedTask.id}/status/desconcluir`)
                .then(response => {
                    setRows(rows.map(row => {
                        if (row.id === selectedTask.id) {
                            return { ...row, completed: true };
                        }
                        return row;
                    }));
                    handleClose();
                    window.location.reload();
                })
                .catch(error => {
                    console.error('Erro ao mudar status da tarefa!', error);
                });
            }else{
                axios.post(`http://localhost:8080/tarefas/${selectedTask.id}/status/concluir`)
                .then(response => {
                    setRows(rows.map(row => {
                        if (row.id === selectedTask.id) {
                            return { ...row, completed: true };
                        }
                        return row;
                    }));
                    handleClose();
                    window.location.reload();
                })
                .catch(error => {
                    console.error('Erro ao mudar status da tarefa!', error);
                });
            }
            
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

    const handleEdit = (task) => {
        // Implementar função de edição, pode abrir um modal com formulário para editar os dados da tarefa
        console.log('Edit task', task);
    };

    return (
        <div className="App">
            <h1>Lista de Tarefas</h1>
            <Grid container md={4} spacing={2} sx={{ marginLeft: "auto", marginRight: "auto", marginTop: "auto" }}>
                <Grid item xs={8}>
                    <TextField id="outlined-basic" label="Título" variant="outlined" />
                </Grid>
                <Grid item xs={4}>
                    <Button variant="contained">+</Button>
                </Grid>
                <Grid item xs={8}>
                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label">Data tarefa</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={age}
                            size="small"
                            label="Data tarefa"
                            onChange={handleChange}
                        >
                            <MenuItem value="LIVRE">Livre</MenuItem>
                            <MenuItem value="DATA">Data</MenuItem>
                            <MenuItem value="PRAZO">Prazo em dias</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
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
                                        <IconButton onClick={() => handleEdit(row)}>
                                        {/* <Icon icon={"bx:edit"} /> */}
                                        </IconButton>
                                        <IconButton onClick={() => {
                                            setSelectedTask(row);
                                            setConfirmDelete(true);
                                            setOpen(true);
                                        }}>
                                            {/* <Icon icon={"mdi:trash"} /> */}
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

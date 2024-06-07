import './App.css';
import {
    Button,
    FormControl,
    Grid,
    InputLabel,
    MenuItem, Paper,
    TextField,
    Container
} from "@mui/material";
import Select from '@mui/material/Select';
import React, { useEffect, useState } from "react";
import axios from "axios";
import { DataGrid } from '@mui/x-data-grid';

function App() {
    const [age, setAge] = useState('');
    const [rows, setRows] = useState([]);
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
    console.log(rows);

    const columns = [
        { field: 'titulo', headerName: 'Título', width: 200 },
        { field: 'prioridade', headerName: 'Prioridade', width: 150 },
        { field: 'status', headerName: 'Status', width: 150 },
        { field: 'tipo', headerName: 'Tipo', width: 150 },
    ];

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
                <Paper style={{ height: 400, width: '100%' }}>
                    <DataGrid
                        rows={rows}
                        columns={columns}
                        initialState={{
                            pagination: {
                                paginationModel: { page: 0, pageSize: 5 },
                            },
                        }}
                        pageSizeOptions={[5, 10]}
                        checkboxSelection
                    />
                </Paper>
            </Container>
        </div>
    );
}

export default App;

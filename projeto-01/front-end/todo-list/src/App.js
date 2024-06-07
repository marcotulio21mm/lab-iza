import './App.css';
import {
    Button,
    FormControl,
    Grid,
    InputLabel,
    MenuItem, Paper,
    Table, TableBody,
    TableContainer,
    TableHead, TableRow,
    TextField,
    TableCell, Container
} from "@mui/material";
import Select  from '@mui/material/Select';
import React, {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [age, setAge] = React.useState('');
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
    console.log(rows)
    function createData(name, calories, fat, carbs, protein) {
        return { name, calories, fat, carbs, protein };
    }

    return (
        <div className="App">
            <h1>Lista de Tarefas</h1>
            <Grid container md={4} spacing={2} sx={{marginLeft:"auto",marginRight:"auto", marginTop:"auto"}}>
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
            <Container sx={{marginTop:"2rem"}}>
                <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="right">Titulo</TableCell>
                                <TableCell align="right">Fat&nbsp;(g)</TableCell>
                                <TableCell align="right">Carbs&nbsp;(g)</TableCell>
                                <TableCell align="right">Protein&nbsp;(g)</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.map((row) => (
                                <TableRow
                                    key={row.name}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="right">{row.titulo}</TableCell>
                                    <TableCell align="right">{row.fat}</TableCell>
                                    <TableCell align="right">{row.carbs}</TableCell>
                                    <TableCell align="right">{row.protein}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Container>
        </div>
    );
}

export default App;

import React from 'react';
import TodoWrapper from './components/TodoWrapper';
import './App.css';

function App() {
  return (
    <div className="App" sx={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center" }}>
      <h1>Lista de Tarefas</h1>
      <TodoWrapper />
    </div>
  );
}

export default App;

$(document).ready(function() {
  loadTasks();
});

// Função para carregar a lista de tarefas
function loadTasks() {
  $.ajax({
      url: 'http://localhost:8080/tarefas',
      method: 'GET',
      success: function(response) {
          if (response.length === 0) {
              $('#taskList').html('<p>Lista vazia</p>');
          } else {
              let html = '';
              response.forEach(task => {
                  html += `
                  <div class="task-item">
                    <div class="task-content">
                        <input type="checkbox" class="form-check-input" data-task-id="${task.id}" ${task.status ? 'checked' : ''}>
                        <span class="checkmark">${task.titulo}</span>
                    </div>
                    <button class="btn btn-delete" data-task-id="${task.id}"><img src="img/Delete.svg" alt="Delete Icon"></button>
                  </div>  
                  `;
              });
              $('#taskList').html(html);
          }
      },
      error: function() {
          $('#taskList').html('<p>Erro ao carregar a lista de tarefas</p>');
      }
  });
}


function createTask() {
  var inputValue = document.getElementById("inputText").value.trim(); // Remova espaços em branco em excesso
    if (inputValue === "") {
        alert("Título vazio! Por favor, preencha o título.");
        return; // Retorna se o título estiver vazio
    }

    $.ajax({
        url: 'http://localhost:8080/tarefas',
        method: 'POST',
        data: { titulo: inputValue }, // Envia o título da nova tarefa como parâmetro de URL
        success: function(response) {
            // Se a tarefa for adicionada com sucesso, recarrega a lista de tarefas
            loadTasks();
        },
        error: function() {
            alert("Erro ao adicionar a tarefa. Por favor, tente novamente.");
        }
    });
}

// Adiciona um ouvinte de evento para mudar o status da tarefa quando o usuário clicar na checkbox
$(document).on('change', '.form-check-input', function() {
  var taskId = $(this).attr('data-task-id');
  var isChecked = $(this).prop('checked');

  // Envia uma solicitação para mudar o status da tarefa com base na marcação da checkbox
  $.ajax({
      url: `http://localhost:8080/tarefas/${taskId}/status`,
      method: 'POST',
      data: { status: isChecked },
      success: function(response) {
          // Se o status da tarefa for alterado com sucesso, recarrega a lista de tarefas
          loadTasks();
      },
      error: function() {
          alert("Erro ao alterar o status da tarefa. Por favor, tente novamente.");
      }
  });
});

// Adiciona um ouvinte de evento para excluir uma tarefa quando o usuário clicar no botão de exclusão
$(document).on('click', '.btn-delete', function() {
  var taskId = $(this).attr('data-task-id');

  // Envia uma solicitação para excluir a tarefa com base no ID fornecido
  $.ajax({
      url: `http://localhost:8080/tarefas/${taskId}`,
      method: 'DELETE',
      success: function(response) {
          // Se a tarefa for excluída com sucesso, recarrega a lista de tarefas
          loadTasks();
      },
      error: function() {
          alert("Erro ao excluir a tarefa. Por favor, tente novamente.");
      }
  });
});



  
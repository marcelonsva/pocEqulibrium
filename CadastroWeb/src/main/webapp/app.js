const processApiUrl = 'http://localhost:8084/api/Processos';
const tipoApiUrl = 'http://localhost:8085/api/tiposDeProcesso';

// Carregar os tipos de processo no formulário
function loadTiposDeProcesso() {
  fetch(tipoApiUrl)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Erro ao carregar tipos de processo: ${response.statusText}`);
      }
      return response.json();
    })
    .then(data => {
      const tipoSelect = document.getElementById('tipo');
      tipoSelect.innerHTML = '<option value="">Selecione</option>';
      data.forEach(tipo => {
        tipoSelect.innerHTML += `<option value="${tipo.id}">${tipo.nome}</option>`;
      });
    })
    .catch(error => {
      console.error('Erro ao carregar os tipos de processo:', error);
      alert('Erro ao carregar os tipos de processo. Verifique a conexão com o servidor.');
    });
}

// Carregar os processos na tabela
function loadProcesses(numero = '') {
  const url = numero ? `${processApiUrl}?numero=${numero}` : processApiUrl;
  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Erro ao carregar processos: ${response.statusText}`);
      }
      return response.json();
    })
    .then(data => {
      const list = document.getElementById('process-list');
      list.innerHTML = '';

      const processes = data.content || data; // Suporte para paginação ou lista simples
      if (processes.length > 0) {
        processes.forEach(process => {
          list.innerHTML += `
            <tr>
              <td>${process.id}</td>
              <td>${process.tipoDeProcesso}</td>
              <td>${process.numero}</td>
              <td>${process.dataEntrada}</td>
              <td>${process.valorRecurso}</td>
              <td>${process.objetivo}</td>
              <td>
                <button onclick="editProcess(${process.id})">Editar</button>
                <button onclick="deleteProcess(${process.id})">Excluir</button>
              </td>
            </tr>
          `;
        });
      } else {
        list.innerHTML = '<tr><td colspan="7">Nenhum processo encontrado.</td></tr>';
      }
    })
    .catch(error => {
      console.error('Erro ao carregar os processos:', error);
      alert('Erro ao carregar os processos. Verifique a conexão com o servidor.');
    });
}

// Preencher formulário ao editar
function editProcess(id) {
  fetch(`${processApiUrl}/${id}`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Erro ao buscar processo: ${response.statusText}`);
      }
      return response.json();
    })
    .then(process => {
      document.getElementById('process-id').value = process.id || '';
      document.getElementById('tipo').value = process.tipoDeProcesso || '';
      document.getElementById('numero').value = process.numero || '';
      document.getElementById('dataEntrada').value = process.dataEntrada || '';
      document.getElementById('valorRecurso').value = process.valorRecurso || '';
      document.getElementById('objetivo').value = process.objetivo || '';
    })
    .catch(error => {
      console.error('Erro ao carregar os dados do processo para edição:', error);
      alert('Erro ao carregar os dados do processo. Tente novamente.');
    });
}

// Criar ou atualizar um processo
document.getElementById('process-form').addEventListener('submit', function (e) {
  e.preventDefault();

  const id = document.getElementById('process-id').value;
  const tipoId = document.getElementById('tipo').value;
  const numero = document.getElementById('numero').value;
  const dataEntrada = document.getElementById('dataEntrada').value;
  const valorRecurso = document.getElementById('valorRecurso').value;
  const objetivo = document.getElementById('objetivo').value;

  if (!tipoId || !numero || !dataEntrada || !valorRecurso || !objetivo) {
    alert('Todos os campos são obrigatórios!');
    return;
  }

  const method = id ? 'PUT' : 'POST';
  const endpoint = id ? `${processApiUrl}/${id}` : processApiUrl;

  const payload = {
    tipoDeProcesso: parseInt(tipoId),
    numero,
    dataEntrada,
    valorRecurso: parseFloat(valorRecurso),
    objetivo
  };

  fetch(endpoint, {
    method: method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Erro ao salvar processo: ${response.statusText}`);
      }
      return response.json();
    })
    .then(() => {
      alert('Processo salvo com sucesso!');
      loadProcesses();
      document.getElementById('process-form').reset();
    })
    .catch(error => {
      console.error('Erro ao salvar o processo:', error);
      alert('Erro ao salvar o processo. Verifique os dados e tente novamente.');
    });
});

// Botão de limpar o formulário
document.getElementById('clear-form').addEventListener('click', () => {
  document.getElementById('process-form').reset();
});

// Pesquisa por número do processo
document.getElementById('search-button').addEventListener('click', () => {
  const numero = document.getElementById('search-numero').value;
  loadProcesses(numero);
});

// Excluir um processo
function deleteProcess(id) {
  if (confirm('Deseja realmente excluir este processo?')) {
    fetch(`${processApiUrl}/${id}`, { method: 'DELETE' })
      .then(response => {
        if (!response.ok) {
          throw new Error(`Erro ao excluir processo: ${response.statusText}`);
        }
        return response.text();
      })
      .then(() => {
        alert('Processo excluído com sucesso!');
        loadProcesses();
      })
      .catch(error => {
        console.error('Erro ao excluir o processo:', error);
        alert('Erro ao excluir o processo. Tente novamente.');
      });
  }
}

// Inicializar
loadTiposDeProcesso();
loadProcesses();

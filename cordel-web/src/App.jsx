import { useState, useEffect } from 'react'
import './index.css'

function App() {
  const [livros, setLivros] = useState([])
  
  // Estados do formulário
  const [titulo, setTitulo] = useState('')
  const [autor, setAutor] = useState('')
  const [isbn, setIsbn] = useState('')
  const [preco, setPreco] = useState('')

  // --- AULA JS 1: Estado de Controle ---
  // Se estiver null, estamos criando. Se tiver um número, estamos editando.
  const [idEdicao, setIdEdicao] = useState(null)

  useEffect(() => {
    buscarLivros()
  }, [])

  function buscarLivros() {
    fetch('http://localhost:8080/livros')
      .then(res => res.json())
      .then(data => setLivros(data))
      .catch(erro => console.error("Erro ao buscar:", erro))
  }

  // --- AULA JS 2: A Função Híbrida (Serve pra criar e editar) ---
  function salvarLivro(event) {
    event.preventDefault()
    
    const dadosLivro = { 
      titulo: titulo, 
      nomeAutor: autor, 
      isbn: isbn, 
      preco: parseFloat(preco) 
    }

    // SE temos um ID de edição, o caminho é PUT (Atualizar)
    if (idEdicao !== null) {
      fetch(`http://localhost:8080/livros/${idEdicao}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosLivro)
      }).then(() => {
        alert("Livro atualizado com sucesso!")
        buscarLivros()
        limparFormulario() // Importante: Voltar ao estado original
      })

    } else {
      // SE NÃO temos ID, o caminho é POST (Criar Novo)
      fetch('http://localhost:8080/livros', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosLivro)
      }).then(() => {
        alert("Livro cadastrado com sucesso!")
        buscarLivros()
        limparFormulario()
      })
    }
  }

  // --- AULA JS 3: Preparando o Terreno ---
  // Pega o livro que foi clicado e joga os dados dele para cima (no form)
  function prepararEdicao(livro) {
    setTitulo(livro.titulo)
    setAutor(livro.nomeAutor)
    setIsbn(livro.isbn)
    setPreco(livro.preco)
    setIdEdicao(livro.id) // Ativa o "Modo Edição"
  }

  // Reseta tudo para o usuário poder cadastrar um novo depois de editar
  function limparFormulario() {
    setTitulo('')
    setAutor('')
    setIsbn('')
    setPreco('')
    setIdEdicao(null) // Desativa o "Modo Edição"
  }

  function removerLivro(id) {
    if (confirm("Tem certeza que deseja excluir este livro?")) {
      fetch(`http://localhost:8080/livros/${id}`, { method: 'DELETE' })
      .then(() => {
        // Se a pessoa deletar o livro que está editando, limpamos o form
        if (id === idEdicao) limparFormulario()
        setLivros(livros.filter(l => l.id !== id))
      })
    }
  }

  return (
    <div className="app-container">
      <h1>Cordel do Saber</h1>
      
      <div className="card-form">
        {/* Muda o título dependendo do modo */}
        <h2>{idEdicao ? 'Editando livro' : 'Novo livro'}</h2>
        
        <form onSubmit={salvarLivro}>
          <input placeholder="Título" value={titulo} onChange={e => setTitulo(e.target.value)} required />
          <input placeholder="Autor" value={autor} onChange={e => setAutor(e.target.value)} required />
          <input placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} required />
          <input placeholder="Preço" type="number" step="0.01" value={preco} onChange={e => setPreco(e.target.value)} required />
          
          <div style={{display: 'flex', gap: '10px', justifyContent: 'center'}}>
            <button type="submit">
              {/* Ternário: Se editando, mostra "Salvar", se não, "Pendurar" */}
              {idEdicao ? 'Salvar Alterações' : 'Cadastrar Livro'}
            </button>

            {/* Botão Cancelar: Só aparece se estiver editando */}
            {idEdicao && (
              <button 
                type="button" 
                onClick={limparFormulario}
                style={{backgroundColor: '#6c757d'}}
              >
                Cancelar
              </button>
            )}
          </div>
        </form>
      </div>

      <div className="lista-livros">
        {livros.map(livro => (
          <div key={livro.id} className="livro-card">
            <h3>{livro.titulo}</h3>
            <p>{livro.nomeAutor}</p>
            <p className="preco">R$ {livro.preco}</p>

            <div style={{display: 'flex', gap: '10px', marginTop: '10px'}}>
              {/* Botão Editar: Chama a função que sobe os dados */}
              <button 
                onClick={() => prepararEdicao(livro)}
                style={{backgroundColor: '#ffc107', color: '#000'}}
              >
                ✏️ Editar
              </button>

              <button 
                onClick={() => removerLivro(livro.id)}
                style={{backgroundColor: '#dc3545'}}
              >
                🗑️ Excluir
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default App
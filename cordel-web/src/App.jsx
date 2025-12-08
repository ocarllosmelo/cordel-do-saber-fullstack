import { useState, useEffect } from 'react'
import './index.css'

function App() {
  const [livros, setLivros] = useState([])
  
  // Estados do formulário
  const [titulo, setTitulo] = useState('')
  const [autor, setAutor] = useState('')
  const [isbn, setIsbn] = useState('')
  const [preco, setPreco] = useState('')

  useEffect(() => {
    buscarLivros()
  }, [])

  function buscarLivros() {
    fetch('http://localhost:8080/livros')
      .then(res => res.json())
      .then(data => setLivros(data))
      .catch(erro => console.error("Erro ao buscar:", erro))
  }

  function salvarLivro(event) {
    event.preventDefault()
    const novoLivro = { titulo, nomeAutor: autor, isbn, preco: parseFloat(preco) }

    fetch('http://localhost:8080/livros', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(novoLivro)
    }).then(() => {
        buscarLivros()
        setTitulo(''); setAutor(''); setIsbn(''); setPreco('')
    })
  }

  // --- NOVA FUNÇÃO: DELETAR ---
  function removerLivro(id) {
    // 1. Confirmação do usuário (pra não apagar sem querer)
    if (confirm("Tem certeza que deseja excluir este livro do sistema?")) {
      
      // 2. Chama o Back-end (Java) para apagar do banco
      fetch(`http://localhost:8080/livros/${id}`, {
        method: 'DELETE'
      })
      .then(() => {
        // 3. SE deu certo no banco, atualizamos a tela VISUALMENTE.
        // AULA JS: .filter cria uma nova lista ignorando o livro que tem esse ID
        const novaLista = livros.filter(livro => livro.id !== id)
        setLivros(novaLista)
      })
      .catch(erro => alert("Erro ao apagar!"))
    }
  }

  return (
    <div className="app-container">
      <h1>Cordel do Saber</h1>
      
      <div className="card-form">
        <h2>Novo Livro</h2>
        <form onSubmit={salvarLivro}>
          <input placeholder="Título" value={titulo} onChange={e => setTitulo(e.target.value)} />
          <input placeholder="Autor" value={autor} onChange={e => setAutor(e.target.value)} />
          <input placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} />
          <input placeholder="Preço" type="number" value={preco} onChange={e => setPreco(e.target.value)} />
          <button type="submit">Cadastrar Livro</button>
        </form>
      </div>

      <div className="lista-livros">
        {livros.map(livro => (
          <div key={livro.id} className="livro-card">
            <h3>{livro.titulo}</h3>
            <p>{livro.nomeAutor}</p>
            <p className="preco">R$ {livro.preco}</p>

            {/* --- NOVO BOTÃO DE EXCLUIR --- 
                AULA JS: Usamos () => para a função não rodar sozinha ao carregar a página.
            */}
            <button 
              onClick={() => removerLivro(livro.id)}
              style={{ backgroundColor: '#ff6b6b', marginTop: '10px' }}
            >
              🗑️ Excluir
            </button>

          </div>
        ))}
      </div>
    </div>
  )
}

export default App
import { useState, useEffect } from 'react'
import './index.css'

function App() {
  const [livros, setLivros] = useState([])
  const [titulo, setTitulo] = useState('')
  const [autor, setAutor] = useState('')
  const [isbn, setIsbn] = useState('')
  const [preco, setPreco] = useState('')
  const [idEdicao, setIdEdicao] = useState(null)
  const [busca, setBusca] = useState('')

  useEffect(() => { buscarLivros() }, [])

  function buscarLivros() {
    fetch('http://localhost:8080/livros')
      .then(res => res.json())
      .then(data => setLivros(data))
      .catch(erro => console.error("Erro ao buscar:", erro))
  }

  function salvarLivro(event) {
    event.preventDefault()
    const dadosLivro = { titulo, nomeAutor: autor, isbn, preco: parseFloat(preco) }

    if (idEdicao !== null) {
      fetch(`http://localhost:8080/livros/${idEdicao}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosLivro)
      }).then(() => { alert("Livro atualizado!"); buscarLivros(); limparFormulario(); })
    } else {
      fetch('http://localhost:8080/livros', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosLivro)
      }).then(() => { alert("Livro cadastrado!"); buscarLivros(); limparFormulario(); })
    }
  }

  function prepararEdicao(livro) {
    setTitulo(livro.titulo); setAutor(livro.nomeAutor); setIsbn(livro.isbn); setPreco(livro.preco); setIdEdicao(livro.id)
  }

  function limparFormulario() {
    setTitulo(''); setAutor(''); setIsbn(''); setPreco(''); setIdEdicao(null)
  }

  function removerLivro(id) {
    if (confirm("Tem certeza que deseja rasgar este folheto do cordel?")) {
      fetch(`http://localhost:8080/livros/${id}`, { method: 'DELETE' })
      .then(() => { if (id === idEdicao) limparFormulario(); setLivros(livros.filter(l => l.id !== id)) })
    }
  }

  const livrosFiltrados = livros.filter(livro => 
    livro.titulo.toLowerCase().includes(busca.toLowerCase()) ||
    livro.nomeAutor.toLowerCase().includes(busca.toLowerCase())
  )

  return (
    <div className="app-container">
      <h1>Cordel do Saber</h1>
      
      <div className="card-form">
        <h2>{idEdicao ? 'Editando Folheto' : 'Novo Folheto'}</h2>
        <form onSubmit={salvarLivro}>
          {/* DIV NOVA: Agrupa os inputs em grid de 2 colunas */}
          <div className="form-grid">
              <input placeholder="Título do Livro" value={titulo} onChange={e => setTitulo(e.target.value)} required />
              <input placeholder="Nome do Autor" value={autor} onChange={e => setAutor(e.target.value)} required />
              <input placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} required />
              <input placeholder="Preço (R$)" type="number" step="0.01" value={preco} onChange={e => setPreco(e.target.value)} required />
          </div>
          
          <div style={{display: 'flex', gap: '10px', justifyContent: 'center', marginTop: '10px'}}>
            <button type="submit">{idEdicao ? 'Salvar Alterações' : 'Pendurar no Cordel'}</button>
            {idEdicao && <button type="button" onClick={limparFormulario} style={{backgroundColor: '#6c757d', color: 'white'}}>Cancelar</button>}
          </div>
        </form>
      </div>

      {/* Barra de Busca com nova classe */}
      <div className="barra-busca">
        <input 
          placeholder="🔍 Buscar folheto por título ou autor..." 
          value={busca}
          onChange={e => setBusca(e.target.value)}
        />
      </div>

      <div className="lista-livros">
        {livrosFiltrados.map(livro => (
          <div key={livro.id} className="livro-card">
            {/* Agrupamos o topo para facilitar o alinhamento vertical */}
            <div className="livro-info-topo">
              <h3>{livro.titulo}</h3>
              <p className="autor">{livro.nomeAutor}</p>
              <p className="isbn">ISBN: {livro.isbn}</p>
            </div>
            
            {/* Preço bem grande no meio */}
            <p className="preco">R$ {livro.preco.toFixed(2)}</p>

            {/* Botões empilhados na base */}
            <div className="acoes-card">
              <button onClick={() => prepararEdicao(livro)} className="btn-editar">
                ✏️ Editar
              </button>
              <button onClick={() => removerLivro(livro.id)} className="btn-excluir">
                🗑️ Excluir
              </button>
            </div>
          </div>
        ))}
        
        {livrosFiltrados.length === 0 && (
          <p style={{gridColumn: '1 / -1', textAlign: 'center', color: '#666', fontSize: '1.2rem', marginTop: '2rem'}}>
            Nenhum folheto encontrado no varal... 🌵
          </p>
        )}
      </div>
    </div>
  )
}

export default App
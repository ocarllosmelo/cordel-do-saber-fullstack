import { useState, useEffect } from 'react'
import './index.css'

function App() {
  // 1. Estado para guardar a lista de livros que vem do Java
  const [livros, setLivros] = useState([])

  // 2. Efeito que roda assim que a página abre
  useEffect(() => {
    // Chama a API Java
    fetch('http://localhost:8080/livros')
      .then(resposta => resposta.json()) // Converte a resposta para JSON
      .then(dados => setLivros(dados))   // Guarda os dados no estado 'livros'
      .catch(erro => console.error("Erro ao buscar livros:", erro))
  }, [])

  return (
    <div className="app-container">
      <h1>Cordel do Saber</h1>
      <p>Bem-vindo à nossa livraria de literatura de cordel. Esses são os nossos livros no momento:</p>

      {/* 3. Lista de Livros */}
      <div className="lista-livros">
        {livros.length === 0 ? (
          <p>Carregando estoque...</p>
        ) : (
          livros.map(livro => (
            <div key={livro.id} className="livro-card">
              <h3>{livro.titulo}</h3>
              <p><strong>Autor:</strong> {livro.nomeAutor}</p>
              <p><strong>Preço:</strong> R$ {livro.preco}</p>
            </div>
          ))
        )}
      </div>
    </div>
  )
}

export default App

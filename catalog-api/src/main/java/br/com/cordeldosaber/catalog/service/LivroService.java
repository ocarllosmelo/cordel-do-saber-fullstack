package br.com.cordeldosaber.catalog.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.cordeldosaber.catalog.repository.LivroRepository;

import java.util.List;
import br.com.cordeldosaber.catalog.model.Livro;

import br.com.cordeldosaber.catalog.dto.LivroDTO;
import java.util.stream.Collectors; // Import para transformar a lista
import jakarta.persistence.EntityNotFoundException;

/**
 * @Service: Anotação do Spring.
 * Esta etiqueta diz ao Spring: "Por favor, crie uma instância (um objeto)
 * desta classe automaticamente. Ela é um 'Componente de Serviço'
 * e fará parte da arquitetura."
 * Isso permite que o Spring a "injete" em outras classes (como o Controller).
 */
@Service
// ... (imports e @Autowired) ...
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // O PRIMEIRO MÉTODO DE NEGÓCIO

    // Método DEPOIS (retorna DTO)
    public List<LivroDTO> buscarTodos() {
        // 1. Busca a lista de Entidades do banco
        List<Livro> listaDeEntidades = livroRepository.findAll();

        // 2. Converte a lista de Entidades em uma lista de DTOs
        //    (Usando o "construtor de cópia" que criamos no DTO)
        List<LivroDTO> listaDeDTOs = listaDeEntidades.stream()
                .map(entidade -> new LivroDTO(entidade))
                .collect(Collectors.toList());

        return listaDeDTOs;
    }

    /**
     * Insere um novo livro no banco de dados.
     * @param dto O "formulário" (LivroDTO) com os dados do novo livro.
     * @return O DTO do livro que foi salvo (agora com o 'id').
     */
    public LivroDTO inserir(LivroDTO dto) {
        // 1. Criar uma Entidade Livro "em branco"
        Livro entidade = new Livro();

        // 2. Copiar os dados do "formulário" (DTO) para a Entidade
        entidade.setTitulo(dto.getTitulo());
        entidade.setNomeAutor(dto.getNomeAutor());
        entidade.setIsbn(dto.getIsbn());
        entidade.setPreco(dto.getPreco());
        // (Não passamos o ID, pois o banco vai gerar)

        // 3. Salvar a Entidade no banco
        // O Repository recebe a Entidade, salva, e o Spring/JPA
        // atualiza nosso objeto 'entidade' com o novo 'id' gerado.
        entidade = livroRepository.save(entidade);

        // 4. Retornar um NOVO DTO baseado na entidade que acabamos de salvar
        return new LivroDTO(entidade);
    }

    /**
     * Atualiza um livro existente no banco de dados.
     * @param id O ID do livro a ser atualizado.
     * @param dto O "formulário" (LivroDTO) com os novos dados.
     * @return O DTO do livro que foi atualizado.
     */
    public LivroDTO atualizar(Long id, LivroDTO dto) {
        // 1. Buscar a Entidade original no banco pelo ID.
        //    Usamos .orElseThrow() para lançar um erro se o ID não existir.
        //    Esta é uma prática melhor do que retornar null.
        try {
            Livro entidade = livroRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com o ID: " + id));

            // 2. Copiar os dados do "formulário" (DTO) para a Entidade encontrada
            entidade.setTitulo(dto.getTitulo());
            entidade.setNomeAutor(dto.getNomeAutor());
            entidade.setIsbn(dto.getIsbn());
            entidade.setPreco(dto.getPreco());

            // 3. Salvar a Entidade atualizada no banco.
            //    Como o 'entidade' TEM um ID, o JpaRepository sabe que deve
            //    fazer um UPDATE (atualizar) em vez de um INSERT (criar).
            entidade = livroRepository.save(entidade);

            // 4. Retornar um novo DTO baseado na entidade atualizada.
            return new LivroDTO(entidade);

        } catch (EntityNotFoundException e) {
            // Em um cenário real, o @ControllerAdvice trataria isso,
            // mas por enquanto, podemos relançar ou tratar aqui.
            // Vamos imprimir o erro e retornar null (ou lançar a exceção).
            System.err.println(e.getMessage());
            // O ideal é ter um @ControllerAdvice para capturar esta exceção
            // e retornar um 404 Not Found.
            throw e; // Repassa a exceção para o Spring tratar
        }
    }

    /**
     * Deleta um livro do banco de dados pelo seu ID.
     * @param id O ID do livro a ser deletado.
     */
    public void deletar(Long id) {
        // 1. Verificar se o livro existe ANTES de tentar deletar.
        //    O método .existsById() é outra mágica do JpaRepository!
        //    Ele faz um "SELECT COUNT(*)" super rápido para ver se o id existe.
        if (!livroRepository.existsById(id)) {
            // Se não existe, lançamos a mesma exceção que usamos no 'atualizar'.
            // Isso garante que o cliente (Postman) receberá um erro (ex: 404 Not Found)
            // se tentar deletar um ID inválido.
            throw new EntityNotFoundException("Livro não encontrado com o ID: " + id);
        }

        // 2. Se o livro existe, o Gerente manda o Bibliotecário deletar.
        livroRepository.deleteById(id);
    }

    // Buscar por id;
    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Livro não encontrado"));

        return new LivroDTO(livro);
    }

    // (Aqui adicionaremos o buscarPorId() e salvar() depois)
}
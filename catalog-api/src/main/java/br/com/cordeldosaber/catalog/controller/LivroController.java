package br.com.cordeldosaber.catalog.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.cordeldosaber.catalog.model.Livro;
import br.com.cordeldosaber.catalog.service.LivroService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import br.com.cordeldosaber.catalog.dto.LivroDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

//Obs.: os comentários nos códigos são para fixar o conteúdo.

/**
 * @RestController: esta anotação tem a função de transformar a classe em um "Controlador REST".
 * O que ela faz? Ela diz ao Spring: "A responsabilidade desta classe é receber requisições HTTP
 * e os resultados dos métodos devem ser convertidos para JSON automaticamente."
 */
@RestController

/**
 * @RequestMapping: define o "endereço base" ou a "rota" para todos os métodos
 * dentro desta classe. Todos os pedidos para esta classe deverão começar com /livros.
 * Ex: http://localhost:8080/livros
 */
@RequestMapping("/livros")
public class LivroController {

    // 1. Injetando o Service.
    // O Controller precisa ter o contato do Service.
    // Por isso, usamos @Autowired para que o Spring nos dê a instância do LivroService.
    @Autowired
    private LivroService livroService;

    // 2. Criando o primeiro "Endpoint".
    // O método retorna DTO.
    @GetMapping
    public List<LivroDTO> buscarTodos() {
        return livroService.buscarTodos();
    }

    // Retorna por id;
    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        LivroDTO dto = livroService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * @PostMapping: aqui, mapeia este método para o verbo HTTP POST (usado para Criar).
     * Ele responderá em "POST /livros".
     */
    @PostMapping
    public ResponseEntity<LivroDTO> inserir(@Valid @RequestBody LivroDTO dto) {
        dto = livroService.inserir(dto);
        URI uri = URI.create("/livros/" + dto.getId());
        return ResponseEntity.created(uri).body(dto);
    }

    /**
     * @PutMapping: mapeia este método para o verbo HTTP PUT (usado para Atualizar).
     * @PathVariable: pega o {id} da URL e o injeta na variável 'id'.
     * @RequestBody: pega o JSON do corpo e o injeta no 'dto'.
     *
     * Endereço completo: (PUT) http://localhost:8080/livros/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LivroDTO dto) {
        LivroDTO dtoAtualizado = livroService.atualizar(id, dto);
        return ResponseEntity.ok().body(dtoAtualizado);
    }

    /**
     * @DeleteMapping: mapeia este método para o verbo HTTP DELETE.
     * @PathVariable: pega o {id} da URL e o injeta na variável 'id'.
     *
     * Endereço completo: (DELETE) http://localhost:8080/livros/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // 1. Delega a tarefa para o Service.
        livroService.deletar(id);

        // 2. Resposta padrão para DELETE (204 No Content).
        //    Quando é bem-sucedida, a convenção REST
        //    retorna o status "204 No Content", que significa:
        //    "veja, fiz o que você pediu (deletei), e agora não tenho
        //    nenhum conteúdo (void) para te retornar no corpo.".
        return ResponseEntity.noContent().build();
    }

}

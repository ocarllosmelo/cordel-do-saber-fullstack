package br.com.cordeldosaber.catalog.exception;

import br.com.cordeldosaber.catalog.dto.ErroCustomizado;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import br.com.cordeldosaber.catalog.dto.ErroValidacao;
import java.time.Instant;

//Obs.: os comentários nos códigos são para fixar o conteúdo.

/**
 * @ControllerAdvice: Diz ao Spring que esta classe vai "ouvir"
 * e tratar exceções de todos os Controllers da aplicação.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * @ExceptionHandler: diz qual tipo de erro este método trata.
     * Sempre que estourar um EntityNotFoundException, este método roda.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroCustomizado> entidadeNaoEncontrada(EntityNotFoundException e, HttpServletRequest request) {

        ErroCustomizado erro = new ErroCustomizado();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.NOT_FOUND.value()); // 404.
        erro.setError("Recurso não encontrado");
        erro.setMessage(e.getMessage()); // A mensagem colocada no Service ("Livro não encontrado...").
        erro.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Tratamento para Erros de Validação (Dados inválidos).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroCustomizado> validacao(MethodArgumentNotValidException e, HttpServletRequest request) {

        // Usando o status 422 (Unprocessable Entity) que é muito usado para erros de validação.
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ErroValidacao erro = new ErroValidacao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(status.value());
        erro.setError("Erro de Validação");
        erro.setMessage("Dados inválidos encontrados");
        erro.setPath(request.getRequestURI());

        // Aqui, é pego a lista de erros que o Spring gerou e é passada para o JSON limpo.
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            erro.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(erro);
    }
}

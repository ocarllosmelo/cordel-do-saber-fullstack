package br.com.cordeldosaber.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

//Obs.: os comentários nos códigos são para fixar o conteúdo.

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroCustomizado {
    private Instant timestamp; // Hora do erro.
    private Integer status;    // Código HTTP (ex: 404).
    private String error;      // Nome do erro (ex: "Recurso não encontrado").
    private String message;    // Mensagem detalhada.
    private String path;       // Qual URL deu erro.
}

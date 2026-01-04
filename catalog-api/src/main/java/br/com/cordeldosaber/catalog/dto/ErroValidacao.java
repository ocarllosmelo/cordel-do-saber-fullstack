package br.com.cordeldosaber.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

//Obs.: os comentários nos códigos são para fixar o conteúdo.

@Data
@EqualsAndHashCode(callSuper = false) // Herda do pai, mas ignora no equals.
public class ErroValidacao extends ErroCustomizado {

    // A lista onde será guardado os erros de cada campo.
    private List<CampoMensagem> errors = new ArrayList<>();

    public void addError(String campo, String mensagem) {
        errors.add(new CampoMensagem(campo, mensagem));
    }
}
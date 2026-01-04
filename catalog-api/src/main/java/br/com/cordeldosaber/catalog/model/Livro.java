package br.com.cordeldosaber.catalog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

//Obs.: os comentários nos códigos são para fixar o conteúdo.

@Data
@NoArgsConstructor
@AllArgsConstructor
// Controla o equals/hashCode para usar apenas o 'id'.
// Evita problemas de performance com coleções.
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_livro") // Nome da tabela.

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 100, nullable = false) // Coluna 'titulo', 100 chars, não pode ser nula.
    private String titulo;

    @Column(name = "nome_autor", length = 150) // Coluna 'nome_autor'.
    private String nomeAutor;

    @Column(name = "isbn", length = 13, unique = true) // O ISBN é único.
    private String isbn;

    @Column(name = "preco", precision = 10, scale = 2) // Ex: 12345678.99.
    private BigDecimal preco;
}
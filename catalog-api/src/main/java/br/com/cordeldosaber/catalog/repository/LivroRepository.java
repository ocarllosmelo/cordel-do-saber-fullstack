package br.com.cordeldosaber.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cordeldosaber.catalog.model.Livro;

//Obs.: os comentários nos códigos são para fixar o conteúdo.

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
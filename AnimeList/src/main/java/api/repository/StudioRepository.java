package api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Studio;

// Repositório responsável por acessar os dados da entidade Studio
public interface StudioRepository extends JpaRepository<Studio, Long> {

    // Busca um estúdio pelo nome exato
    Studio findByName(String name);

    // Busca todos os estúdios cujo nome contenha a string informada, ignorando maiúsculas/minúsculas
    List<Studio> findByNameContainingIgnoreCase(String nome);
}
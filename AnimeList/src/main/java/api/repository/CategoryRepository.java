package api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Category;

// Repositório responsável por acessar os dados da entidade Category
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	// Busca uma categoria pelo nome exato
    Category findByName(String name);
    
    // Busca todas as categorias cujo nome contenha a string informada, ignorando maiúsculas/minúsculas
    List<Category> findByNameContainingIgnoreCase(String nome);
}
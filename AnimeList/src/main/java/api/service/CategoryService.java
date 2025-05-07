package api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import api.entity.Category;
import api.repository.CategoryRepository;

@Service
public class CategoryService {

	// Instância única do repository
	private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
	
	// Tratamento se existe a categoria ou ela precisa ser criada;
    public List<Category> getOrCreateCategories(List<Category> categories) {
        List<Category> result = new ArrayList<>();

        for (Category category : categories) {
            Category existing = categoryRepository.findByName(category.getName());
            if (existing != null) {
                result.add(existing);
            } else {
                result.add(categoryRepository.save(category));
            }
        }
        return result;
    }
    
    // Lista todas as categorias
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    // Busca categoria por ID
    public Category findById(long id) {
        return this.categoryRepository.findById(id).get();
    }

    // Busca por nome (parcial, ignorando maiúsculas/minúsculas)
    public List<Category> findByName(String nome) {
        return this.categoryRepository.findByNameContainingIgnoreCase(nome);
    }

    // Salvar nova categoria
    public String save(Category category) {
        this.categoryRepository.save(category);
        return "Categoria salva com sucesso";
    }

    // Atualizar categoria existente
    public String update(Category category, long id) {
        category.setId(id);
        this.categoryRepository.save(category);
        return "Categoria atualizada com sucesso";
    }

    // Deletar categoria por ID
    public String deleteById(long id) {
        this.categoryRepository.deleteById(id);
        return "Categoria deletada com sucesso";
    }
}
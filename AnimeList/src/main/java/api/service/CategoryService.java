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
    
}
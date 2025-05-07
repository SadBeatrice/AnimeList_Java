package api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    public List<Category> findByNameContainingIgnoreCase(String nome);
}
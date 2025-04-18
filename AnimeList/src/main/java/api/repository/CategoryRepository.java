package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
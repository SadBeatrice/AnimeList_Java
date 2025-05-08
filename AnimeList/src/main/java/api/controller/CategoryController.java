package api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import api.entity.Category;
import api.service.CategoryService;

@RestController 					// Define esta classe como um controlador REST
@RequestMapping("/api/category") 	// Define o caminho base para todos os endpoints de categoria
@CrossOrigin("*") 					// Permite requisições de qualquer origem (CORS)
public class CategoryController {

	// Injeta o CategoryService
    @Autowired 
    private CategoryService categoryService;

    // Retorna todas as categorias cadastradas (requer papel ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findAll")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> lista = this.categoryService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Retorna uma categoria com base no ID informado (requer papel ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") long id) {
        Category category = this.categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Remove uma categoria com base no ID informado (requer papel ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        String mensagem = this.categoryService.deleteById(id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    // Salva uma nova categoria (requer papel ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Category category) {
        String mensagem = this.categoryService.save(category);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    // Atualiza uma categoria existente com base no ID (requer papel ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Category category, @PathVariable("id") long id) {
        String mensagem = this.categoryService.update(category, id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }
}

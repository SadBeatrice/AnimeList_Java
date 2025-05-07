package api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.entity.Category;
import api.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> lista = this.categoryService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<Category>> findByNome(@RequestParam("nome") String nome) {
        List<Category> lista = this.categoryService.findByName(nome);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") long id) {
        Category category = this.categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        String mensagem = this.categoryService.deleteById(id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Category category) {
        String mensagem = this.categoryService.save(category);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Category category, @PathVariable("id") long id) {
        String mensagem = this.categoryService.update(category, id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }
}

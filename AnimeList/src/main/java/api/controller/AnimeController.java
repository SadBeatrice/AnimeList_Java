package api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import api.entity.Anime;
import api.service.AnimeService;

@RestController					// Define esta classe como um controlador REST
@RequestMapping("/api/anime")	// Define o caminho base para todos os endpoints de categoria
@CrossOrigin("*")				// Permite requisições de qualquer origem (CORS)
public class AnimeController {

	// Injeta o AnimeService
	@Autowired
	private AnimeService animeService;

    // Salva um novo anime (caso não haja erro)
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Anime anime){
        try {
            String mensagem = this.animeService.save(anime);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED); // Retorna sucesso com código 201 (Created)
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao tentar salvar.", HttpStatus.BAD_REQUEST); // Retorna erro com código 400 (Bad Request)
        }
    }

    // Atualiza um anime existente baseado no ID (caso não haja erro)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Anime anime, @PathVariable long id){
        try {
            String mensagem = this.animeService.update(anime, id);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED); // Retorna sucesso com código 201 (Created)
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao alterar.", HttpStatus.BAD_REQUEST); // Retorna erro com código 400 (Bad Request)
        }
    }

    // Deleta um anime com base no ID (caso não haja erro)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        try {
            String mensagem = this.animeService.deleteById(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK); // Retorna sucesso com código 200 (OK)
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar.", HttpStatus.BAD_REQUEST); // Retorna erro com código 400 (Bad Request)
        }
    }

    // Retorna todos os animes (caso não haja erro)
    @GetMapping("/findAll")
    public ResponseEntity<List<Anime>> findAll (){
        try {
            List<Anime> lista = this.animeService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK); // Retorna sucesso com código 200 (OK)
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Retorna erro com código 400 (Bad Request)
        }
    }

    // Retorna um anime baseado no ID informado (caso não haja erro)
    @GetMapping("/findById/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id){
        try {
            Anime anime = this.animeService.findById(id);
            return new ResponseEntity<>(anime, HttpStatus.OK); // Retorna sucesso com código 200 (OK)
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Retorna erro com código 400 (Bad Request)
        }
    }
}
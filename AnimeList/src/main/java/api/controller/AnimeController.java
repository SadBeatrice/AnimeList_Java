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

@RestController
@RequestMapping("/api/anime")
@CrossOrigin("*")
public class AnimeController {

	@Autowired
	private AnimeService animeService;

	// Método Save - POST
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Anime anime){
		try {
			String mensagem = this.animeService.save(anime);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao tentar salvar.", HttpStatus.BAD_REQUEST);
		}
	}

	// Método Update - PUT
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Anime anime, @PathVariable long id){
		try {
			String mensagem = this.animeService.update(anime, id);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao alterar.", HttpStatus.BAD_REQUEST);
		}
	}

	// Método delete - DELETE
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id){
		try {
			String mensagem = this.animeService.deleteById(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao deletar.", HttpStatus.BAD_REQUEST);
		}
	}

	// Método Find All - GET
	@GetMapping("/findAll")
	public ResponseEntity<List<Anime>> findAll (){
		try {
			List<Anime> lista = this.animeService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<> (null , HttpStatus.BAD_REQUEST);
		}
	}

	// Método Find By Id - GET
	@GetMapping("/findById/{id}")
	public ResponseEntity<Anime> findById(@PathVariable Long id){
		try {
			Anime anime = this.animeService.findById(id);
			return new ResponseEntity<>(anime, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}

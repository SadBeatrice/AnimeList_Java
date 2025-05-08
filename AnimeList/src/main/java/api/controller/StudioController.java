package api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import api.entity.Studio;
import api.service.StudioService;

@RestController					// Define esta classe como um controlador REST;
@RequestMapping("/api/studio")	// Define a rota base;
@CrossOrigin("*")				// Permite requisições de qualquer origem
public class StudioController {

	// Injeta o StudioService
	@Autowired
	private StudioService studioService;

	// Retorna todos os estúdios cadastrados (requer papel ADMIN)
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findAll")
	public ResponseEntity<List<Studio>> findAll(){
		List<Studio> lista = this.studioService.findAll();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	// Retorna um estúdio com base no ID informado (requer papel ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findById/{id}")
	public ResponseEntity<Studio> findById(@PathVariable("id") long id){
		Studio studio = this.studioService.findById(id);
		return new ResponseEntity<>(studio, HttpStatus.OK);
	}

	// Remove um estúdio com base no ID informado (requer papel ADMIN)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") long id){
		String mensagem = this.studioService.deleteById(id);
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	// Salva um novo estúdio (requer papel ADMIN)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Studio studio){
		String mensagem = this.studioService.save(studio);
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	// Atualiza um estúdio existente com base no ID (requer papel ADMIN)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Studio studio, @PathVariable("id") long id){
		String mensagem = this.studioService.update(studio, id);
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}
}
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.entity.Studio;
import api.service.StudioService;


@RestController
@RequestMapping("/api/studio")
@CrossOrigin("*")
public class StudioController {

	@Autowired
	private StudioService studioService;

	@GetMapping("/findAll")
	public ResponseEntity<List<Studio>> findAll(){
		List<Studio> lista = this.studioService.findAll();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/findByNome")
	public ResponseEntity<List<Studio>> findByNome(@RequestParam("nome") String nome){
		List<Studio> lista = this.studioService.findByName(nome);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Studio> findById(@PathVariable("id") long id){
		Studio studio = this.studioService.findById(id);
		return new ResponseEntity<>(studio, HttpStatus.OK);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") long id){
		String mensagem = this.studioService.deleteById(id);
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Studio studio){
		String mensagem = this.studioService.save(studio);
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Studio studio, @PathVariable("id") long id){
		String mensagem = this.studioService.update(studio, id);
		return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

}
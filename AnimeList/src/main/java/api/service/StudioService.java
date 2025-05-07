package api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import api.entity.Studio;
import api.repository.StudioRepository;

@Service
public class StudioService {

	// Instância única do repository
    private final StudioRepository studioRepository;
    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    // Tratamento se existe o estúdio ou ele precisa ser criado;
    public Studio getOrCreateStudio(Studio studio) {
        Studio existing = studioRepository.findByName(studio.getName());
        return (existing != null) ? existing : studioRepository.save(studio);
    }
    
    public List<Studio> findAll(){
		return this.studioRepository.findAll();
	}
	
	public Studio findById(long id) {
		return this.studioRepository.findById(id).get();
	}
	
	public List<Studio> findByName(String nome) {
		return this.studioRepository.findByNameContainingIgnoreCase(nome);
	}
	
	public String save(Studio studio) {
		this.studioRepository.save(studio);
		return "Estudio salvo com sucesso";
	}
	
	public String update(Studio studio, long id) {
		studio.setId(id);
		this.studioRepository.save(studio);
		return "Estudio atualizado com sucesso";
	}
	
	public String deleteById(long id) {
		this.studioRepository.deleteById(id);
		return "Estudio deletado com sucesso";
	}

}
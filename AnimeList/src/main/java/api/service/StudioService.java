package api.service;

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

    // Tratamento se existe a categoria ou ela precisa ser criada;
    public Studio getOrCreateStudio(Studio studio) {
        Studio existing = studioRepository.findByName(studio.getName());
        return (existing != null) ? existing : studioRepository.save(studio);
    }
}
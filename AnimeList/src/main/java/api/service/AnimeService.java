package api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import api.entity.Anime;
import api.repository.AnimeRepository;


@Service
public class AnimeService {

	private final AnimeRepository animeRepository;
    private final CategoryService categoryService;
    private final StudioService studioService;

    public AnimeService(
        AnimeRepository animeRepository,
        CategoryService categoryService,
        StudioService studioService
    ) {
        this.animeRepository = animeRepository;
        this.categoryService = categoryService;
        this.studioService = studioService;
    }


	// SAVE
    public String save(Anime anime) {
        anime.setCategories(categoryService.getOrCreateCategories(anime.getCategories()));
        anime.setStudio(studioService.getOrCreateStudio(anime.getStudio()));

        animeRepository.save(anime);
        return "Anime salvo com sucesso!";
    }

    // UPDATE
	public String update(Anime anime, Long id) {
		
		Optional<Anime> existingAnimeOpt = animeRepository.findById(id);
	    if (existingAnimeOpt.isEmpty()) {
	        return "Anime não encontrado!";
	    }
	    
		anime.setId(id);
		
		anime.setCategories(categoryService.getOrCreateCategories(anime.getCategories()));
        anime.setStudio(studioService.getOrCreateStudio(anime.getStudio()));

        animeRepository.save(anime);
		return "Anime atualizado com sucesso!";
	}

	// DELETE
	public String deleteById(Long id) {
		this.animeRepository.deleteById(id);
		return "Anime deletado com sucesso!";
	}

	// FIND ALL
	public List<Anime> findAll(){
		List<Anime> anime = this.animeRepository.findAll();
		return anime;
	}

	// FIND BY ID
	public Anime findById(Long id) {
		Anime anime = this.animeRepository.findById(id).get();
		return anime;
	}

}
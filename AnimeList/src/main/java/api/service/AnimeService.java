package api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import api.entity.Anime;
import api.repository.AnimeRepository;

@Service
public class AnimeService {

    // Injeção das dependências necessárias: repositório de animes, serviço de categorias e serviço de estúdios
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

    // Salva um novo anime, tratando categorias e estúdio
    public String save(Anime anime) {
        anime.setCategories(categoryService.getOrCreateCategories(anime.getCategories()));
        anime.setStudio(studioService.getOrCreateStudio(anime.getStudio()));

        animeRepository.save(anime);
        return "Anime salvo com sucesso!";
    }

    // Atualiza um anime existente com base no ID, incluindo tratamento de categorias e estúdio
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

    // Remove um anime com base no ID
    public String deleteById(Long id) {
        this.animeRepository.deleteById(id);
        return "Anime deletado com sucesso!";
    }

    // Retorna a lista de todos os animes
    public List<Anime> findAll() {
        return this.animeRepository.findAll();
    }

    // Busca um anime específico pelo ID
    public Anime findById(Long id) {
        return this.animeRepository.findById(id).get();
    }
}

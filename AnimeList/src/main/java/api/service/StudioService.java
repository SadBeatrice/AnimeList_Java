package api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import api.entity.Studio;
import api.repository.StudioRepository;

@Service
public class StudioService {

    // Instância única do repositório de estúdios
    private final StudioRepository studioRepository;
    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    // Verifica se o estúdio já existe pelo nome; se não existir, salva um novo
    public Studio getOrCreateStudio(Studio studio) {
        Studio existing = studioRepository.findByName(studio.getName());
        return (existing != null) ? existing : studioRepository.save(studio);
    }

    // Lista todos os estúdios cadastrados
    public List<Studio> findAll() {
        return this.studioRepository.findAll();
    }

    // Busca um estúdio pelo ID
    public Studio findById(long id) {
        return this.studioRepository.findById(id).get();
    }

    // Busca estúdios cujo nome contenha o valor informado (ignorando maiúsculas/minúsculas)
    public List<Studio> findByName(String nome) {
        return this.studioRepository.findByNameContainingIgnoreCase(nome);
    }

    // Salva um novo estúdio
    public String save(Studio studio) {
        this.studioRepository.save(studio);
        return "Estudio salvo com sucesso";
    }

    // Atualiza os dados de um estúdio já existente com base no ID
    public String update(Studio studio, long id) {
        studio.setId(id);
        this.studioRepository.save(studio);
        return "Estudio atualizado com sucesso";
    }

    // Remove um estúdio com base no ID
    public String deleteById(long id) {
        this.studioRepository.deleteById(id);
        return "Estudio deletado com sucesso";
    }
}

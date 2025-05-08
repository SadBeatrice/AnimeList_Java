package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Anime;

//Repositório responsável por acessar os dados da entidade Anime
public interface AnimeRepository extends JpaRepository<Anime, Long>{
}

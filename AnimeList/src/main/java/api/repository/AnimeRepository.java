package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long>{
	Anime findByName(String name);
}

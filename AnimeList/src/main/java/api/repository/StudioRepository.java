package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Studio findByName(String name);
}
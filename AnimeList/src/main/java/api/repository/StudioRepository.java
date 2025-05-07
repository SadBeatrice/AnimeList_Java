package api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import api.entity.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Studio findByName(String name);
    public List<Studio> findByNameContainingIgnoreCase(String nome);
}
package api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;
	
	@JsonIgnoreProperties("categories")
    @ManyToMany(mappedBy = "categories")
    private List<Anime> animes;

	
	// Construtores
	public Category() {}
	public Category(String name) {this.name = name;}

	// Getters & Setters
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public List<Anime> getAnimes() {return animes;}
    public void setAnimes(List<Anime> animes) {this.animes = animes;}
    
}
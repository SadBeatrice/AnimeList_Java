package api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "animes")
public class Anime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(nullable = false)
    private String name;
    
	@Column(columnDefinition = "TEXT")
    private String synopsis;
	
	@Column(name = "release_year", nullable = false)
    private Integer year;
    
	@JsonIgnoreProperties("animes")
    @ManyToOne(cascade = {CascadeType.ALL})
    private Studio studio;
    
	@JsonIgnoreProperties("animes")
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "anime_category", 
        joinColumns = @JoinColumn(name = "anime_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
	
    
    // Constructors
    public Anime() {}
    public Anime(String name, String synopsis, Integer year, Studio studio, List<Category> categories) {
    	this.name = name;
        this.synopsis = synopsis;
        this.year = year;
        this.studio = studio;
    }
	
	// Getters & Setters
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getSynopsis() {return synopsis;}
	public void setSynopsis(String synopsis) {this.synopsis = synopsis;}
	public Studio getStudio() {return studio;}
	public void setStudio(Studio studio) {this.studio = studio;}
	public int getYear() {return year;}
	public void setYear(Integer year) {this.year = year;}
	public List<Category> getCategories() {return categories;}
	public void setCategories(List<Category> categories) {this.categories = categories;}
}

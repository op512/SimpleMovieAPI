package uk.co.op.movieapi.controller.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Director {
	
	@Column(name="DirectorId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	public Director() {
	}

	public Director(Integer id, String name, Integer birthYear) {
		this.id = id;
		this.name = name;
		this.birthYear = birthYear;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "director")
	private List<Movie> movies;
	
	private Integer birthYear;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthYear, id, movies, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Director other = (Director) obj;
		return Objects.equals(birthYear, other.birthYear) && Objects.equals(id, other.id)
				&& Objects.equals(movies, other.movies) && Objects.equals(name, other.name);
	}

}

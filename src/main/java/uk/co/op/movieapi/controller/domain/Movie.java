package uk.co.op.movieapi.controller.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import uk.co.op.movieapi.ValidationGroups.Create;

@Entity
public class Movie {
	
	@Column(name="MovieId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY)
	private Integer id;
	
	@Column
	@NotNull(groups = { Create.class })
	private String title;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DirectorId")
	@NotNull(groups = Create.class)
	@Valid
	private Director director;
	
	@NotNull(groups = Create.class)
	private Integer releaseYear;
	
	public Movie() {
	}

	public Movie(Integer id, String title, Director director, Integer releaseYear) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.releaseYear = releaseYear;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	@Override
	public int hashCode() {
		return Objects.hash(director, id, releaseYear, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return Objects.equals(director, other.director) && id == other.id
				&& Objects.equals(releaseYear, other.releaseYear) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", director=" + director + ", releaseYear=" + releaseYear + "]";
	}
	
}

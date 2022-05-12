package uk.co.op.movieapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uk.co.op.movieapi.ValidationGroups.Create;
import uk.co.op.movieapi.ValidationGroups.Update;
import uk.co.op.movieapi.controller.domain.Director;
import uk.co.op.movieapi.controller.domain.Movie;
import uk.co.op.movieapi.dao.DirectorRepository;
import uk.co.op.movieapi.dao.MovieRepository;

@RestController
public class MovieController {
	
	@Autowired
	DirectorRepository directorDao;
	
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	MovieMapper movieMapper;

	@GetMapping("movie")
	public Iterable<Movie> getMovie(@RequestParam(required = false) String title,
			@RequestParam(required = false) Integer releaseYear,
			@RequestParam(required = false) String director) {
		
		Movie movie = new Movie(null, title, director != null ? new Director(null, director, null) : null, releaseYear);
		
		if(new Movie().equals(movie)) {
			return movieRepository.findAll();
		}
		return movieRepository.findAll(Example.of(movie, ExampleMatcher.matchingAny().withStringMatcher(StringMatcher.CONTAINING)));
	}
	
	@GetMapping("movie/{id}")
	public Movie getMovie(@PathVariable Integer id) {
		return movieRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PatchMapping("movie/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable Integer id,
			@RequestBody @Validated @ConvertGroup(from = Default.class, to = Update.class) Movie movie) {
		Movie existing = getMovie(id);
		movieMapper.mergeIgnoreNull(movie, existing);
		movieRepository.save(existing);
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("movie/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
		getMovie(id);
		movieRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}

	@GetMapping("director")
	public List<Director> getDirectors() {
		return directorDao.findAll();
	}

	@PostMapping("movie")
	public ResponseEntity<?> createMovie(
			@RequestBody @Validated(Create.class) Movie movie) {
		movieRepository.save(movie);
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movie.getId()).toUri())
				.build();
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}

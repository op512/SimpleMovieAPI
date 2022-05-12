package uk.co.op.movieapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import uk.co.op.movieapi.controller.MovieController;
import uk.co.op.movieapi.controller.MovieMapperImpl;
import uk.co.op.movieapi.controller.domain.Director;
import uk.co.op.movieapi.controller.domain.Movie;
import uk.co.op.movieapi.dao.DirectorRepository;
import uk.co.op.movieapi.dao.MovieRepository;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
	
	@Mock
	DirectorRepository directorRepository;
	
	@Mock
	MovieRepository movieRepository;

	@Spy
	MovieMapperImpl movieMapper;

	@InjectMocks
	MovieController movieController;
	
	@Test
	void testDeleteMovie() {
		when(movieRepository.findById(3)).thenReturn(Optional.of(new Movie()));
		movieController.deleteMovie(3);
		verify(movieRepository).deleteById(3);
	}

	@Test
	void testDeleteMovieDoesNotExist() {
		ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> movieController.deleteMovie(3));
		assertEquals(404, ex.getRawStatusCode());
	}

	@Test
	void testUpdateMovie() {
		when(movieRepository.findById(3))
				.thenReturn(Optional.of(new Movie(3, "title", new Director(1, "name", 1960), 2000)));
		movieController.updateMovie(3, new Movie(null, "title2", null, null));
		verify(movieRepository).save(new Movie(3, "title2", new Director(1, "name", 1960), 2000));
	}

	@Test
	void testUpdateMovieDoesNotExist() {
		ResponseStatusException ex = assertThrows(ResponseStatusException.class,
				() -> movieController.updateMovie(3, new Movie()));
		assertEquals(404, ex.getRawStatusCode());
	}

	@Test
	void testGetDirectors() {
		when(directorRepository.findAll()).thenReturn(List.of(new Director()));
		
		assertEquals(List.of(new Director()), movieController.getDirectors());
	}

	// similar tests for other methods

}

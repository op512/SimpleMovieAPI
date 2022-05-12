package uk.co.op.movieapi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import uk.co.op.movieapi.controller.domain.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer>, QueryByExampleExecutor<Movie> {
	

}

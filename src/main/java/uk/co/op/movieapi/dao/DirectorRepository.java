package uk.co.op.movieapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.co.op.movieapi.controller.domain.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

	
	
	
}

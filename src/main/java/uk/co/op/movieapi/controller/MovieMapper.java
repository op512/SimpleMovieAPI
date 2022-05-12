package uk.co.op.movieapi.controller;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import uk.co.op.movieapi.controller.domain.Movie;

@Mapper(componentModel = "spring")
public interface MovieMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void mergeIgnoreNull(Movie source, @MappingTarget Movie target);
}

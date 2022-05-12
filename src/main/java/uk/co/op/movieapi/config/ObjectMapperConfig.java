package uk.co.op.movieapi.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Configuration
public class ObjectMapperConfig {
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder.mixIn(Object.class, Mix.class).serializationInclusion(Include.NON_NULL);
	}
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	static class Mix{
		
	}
	

}

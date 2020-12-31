package br.com.timesheetio.person.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeansConfig {
	
	@Bean
	public ModelMapper getModelMapper() {
		
		return new ModelMapper();
	}
}

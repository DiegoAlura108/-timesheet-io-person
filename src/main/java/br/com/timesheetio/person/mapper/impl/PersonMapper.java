package br.com.timesheetio.person.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.timesheetio.person.domain.PersonEntity;
import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.mapper.IMapper;

@Component
@Qualifier("personMapper")
public class PersonMapper implements IMapper<PersonEntity, PersonDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PersonDTO convertEntityToDto(PersonEntity e) {
		return modelMapper.map(e, PersonDTO.class);
	}

	@Override
	public PersonEntity convertDtoToEntity(PersonDTO d) {
		return modelMapper.map(d, PersonEntity.class);
	}
}

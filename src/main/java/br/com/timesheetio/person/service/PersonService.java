package br.com.timesheetio.person.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.timesheetio.person.domain.PersonEntity;
import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.exception.ObjectAlreadyExistsException;
import br.com.timesheetio.person.exception.ObjectNotFoundException;
import br.com.timesheetio.person.mapper.impl.PersonMapper;
import br.com.timesheetio.person.repository.PersonRepository;

@Service
public class PersonService {

	private static final String MESSAGE_OBJECT_ALREADY_EXIST = "This Person Object Already Exists.";

	private static final String MESSAGE_OBJECT_NOT_FOUND = "This Person Object Not Found.";
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonMapper mapper;
	
	public Page<PersonDTO> findAll(Pageable pageable) {
		
		return personRepository.findAll(pageable).map(mapper::convertEntityToDto);
	}
	
	public PersonDTO findById(Long id) {
		
		Optional<PersonEntity> optionalPersonEntity = personRepository.findById(id);
		
		if(optionalPersonEntity.isPresent()) {
			
			return mapper.convertEntityToDto(optionalPersonEntity.get());
		} 
		
		throw new ObjectNotFoundException(PersonService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
	
	public PersonDTO update(PersonDTO personDTO) {
		
		PersonDTO personFound = this.findById(personDTO.getId());
		
		PersonEntity personEntity = mapper.convertDtoToEntity(personFound);
		
		return mapper.convertEntityToDto(personRepository.save(personEntity));
	}
	
	public void delete(Long id) {
		
		PersonDTO personFound = this.findById(id);
		
		personRepository.delete(mapper.convertDtoToEntity(personFound));
	}
	
	public PersonDTO save(PersonDTO personDTO) {
		
		if(personDTO.getId() != null) {
			
			throw new ObjectAlreadyExistsException(PersonService.MESSAGE_OBJECT_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		
		PersonEntity personEntity = mapper.convertDtoToEntity(personDTO);
		
		return mapper.convertEntityToDto(personRepository.save(personEntity));
	}
}

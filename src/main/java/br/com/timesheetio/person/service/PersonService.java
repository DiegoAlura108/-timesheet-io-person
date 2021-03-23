package br.com.timesheetio.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.timesheetio.person.client.AuthFeignClient;
import br.com.timesheetio.person.domain.PersonEntity;
import br.com.timesheetio.person.dto.PersonAuthDTO;
import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.dto.ResponseDTO;
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
	private AuthFeignClient authFeignClient;
	
	@Autowired
	private PersonMapper mapper;
	
	public Page<PersonDTO> findAll(Pageable pageable) {
		
		return personRepository.findAll(pageable).map(mapper::convertEntityToDto);
	}
	
	public PersonDTO findById(Long id) {
		
		PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(PersonService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		
		return mapper.convertEntityToDto(personEntity);
	}
	
	public PersonDTO update(PersonDTO personDTO) {
		
		PersonDTO personFound = this.findById(personDTO.getId());
		
		PersonEntity personEntity = mapper.convertDtoToEntity(personFound);
		
		authFeignClient.updatePersonAuth(personDTO.getPersonAuth()).getBody();
		
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
		
		personDTO.getPersonAuth().setAccountNonExpired(true);
		personDTO.getPersonAuth().setAccountNonLocked(true);
		personDTO.getPersonAuth().setCredentialsNonExpired(true);
		personDTO.getPersonAuth().setEnabled(true);
		
		ResponseDTO<PersonAuthDTO> responsePersonAuth = authFeignClient.savePersonAuth(personDTO.getPersonAuth()).getBody();
		
		PersonEntity personEntity = mapper.convertDtoToEntity(personDTO);
		
		if(responsePersonAuth.getStatus() == 201) {
			personEntity.setPersonAuthUserKey(responsePersonAuth.getData().getPersonAuthUserKey());
		}
		
		return mapper.convertEntityToDto(personRepository.save(personEntity));
	}
}

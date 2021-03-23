package br.com.timesheetio.person.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.timesheetio.person.domain.PersonEntity;
import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.enums.PersonType;
import br.com.timesheetio.person.mapper.impl.PersonMapper;
import br.com.timesheetio.person.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class PersonServiceIntegratedTest {

	private static final Integer PAGE_NUMBER = 0;

	private static final Integer PAGE_LIMIT = 12;

	private static final String PAGE_ORDERED = "id";

	@Mock
	private PersonRepository personRepository;
	
	@Mock	
	private PersonMapper personMapper;
	
	@InjectMocks
	private PersonService personService;
	
	public static PersonEntity personEntity;
	
	public static PersonDTO personDto;
	
	@BeforeAll
	public static void beforeAll() {
		
		personEntity = new PersonEntity();
		personEntity.setId(1L);
		personEntity.setSocialRason("");
		personEntity.setFirstName("Diego");
		personEntity.setLastName("Cordeiro");
		personEntity.setNickName("Feijao Riollando e.E");
		personEntity.setAge(25);
		personEntity.setDocument("39.726.836-1");
		personEntity.setPersonType(PersonType.FISIC);
		personEntity.setPersonAuthUserKey(new BCryptPasswordEncoder().encode("123456789"));
		
		personDto = new PersonDTO();
		personDto.setId(1L);
		personDto.setSocialRason("");
		personDto.setFirstName("Diego");
		personDto.setLastName("Cordeiro");
		personDto.setNickName("Feijao Riollando e.E");
		personDto.setAge(25);
		personDto.setDocument("39.726.836-1");
		personDto.setPersonType(PersonType.FISIC);
	}
	
	@Test
	public void testFindAll() {
		
		Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_LIMIT, Sort.by(Direction.ASC, PAGE_ORDERED));
		
		List<PersonEntity> persons = new ArrayList<>();
		
		persons.add(personEntity);
		
		Mockito.when(personRepository.findAll(pageable)).thenReturn(new PageImpl<PersonEntity>(persons));
		
		Page<PersonDTO> personPage = personService.findAll(pageable);
		
		assertNotNull(personPage);
	}
	
	@Test
	public void testFindById() {
		
		Optional<PersonEntity> optionalPerson = Optional.ofNullable(personEntity == null ? new PersonEntity() : personEntity);
		
		Mockito.when(personRepository.findById(personEntity.getId())).thenReturn(optionalPerson);
		Mockito.when(personMapper.convertEntityToDto(personEntity)).thenReturn(personDto);
		
		PersonDTO person = personService.findById(personEntity.getId());
		
		assertNotNull(person);
		
		Mockito.verify(personRepository, times(1)).findById(personEntity.getId());
	}
}

package br.com.timesheetio.person.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.timesheetio.person.client.AuthFeignClient;
import br.com.timesheetio.person.dto.AddressDTO;
import br.com.timesheetio.person.dto.PersonAuthDTO;
import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.dto.ResponseDTO;
import br.com.timesheetio.person.enums.PersonType;
import br.com.timesheetio.person.service.PersonService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@RunWith(JUnitPlatform.class)
public class PersonResourceUnitTest {

	private static final Integer PAGE_NUMBER = 0;

	private static final Integer PAGE_LIMIT = 12;

	private static final String PAGE_ORDERED = "id";
	
	@LocalServerPort
	private int port;
	
	@MockBean
	private PersonService personService;
	
	@MockBean
	private AuthFeignClient authFeignClient;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private static PersonDTO personDTO;
	
	private static ResponseDTO<PersonAuthDTO> personAuthResponseDTO;
	
	private static PersonAuthDTO personAuthDTO;
	
	@BeforeAll
	public static void init() {
		
		personAuthResponseDTO = new ResponseDTO<PersonAuthDTO>();
		
		personAuthDTO = PersonAuthDTO.builder()
					 .email("diego.test@gmail.com")
					 .password("123456789")
					 .personAuthUserKey("db6634ca8589495fb3e15043e298bc91")
					 .build();
		
		personAuthResponseDTO.setStatus(201);
		personAuthResponseDTO.setData(personAuthDTO);
		
		personDTO = PersonDTO.builder().id(1l)
				 		   .firstName("Diego")
				 		   .lastName("Cordeiro")
				 		   .nickName("Cabeca de Feijao")
				 		   .document("39.726.836-1")
				 		   .personType(PersonType.FISIC)
				 		   .socialRason("")
				 		   .age(25)
				 		   .createdDate(LocalDate.now())
				 		   .address(AddressDTO.builder().id(1l)
				 				   						.neighborhood("Serraria")
				 				   						.street("Rua Milton Patricio de Oliveira")
				 				   						.cep("09980490")
				 				   						.city("Diadema")
				 				   						.reference("State School Santa Maria")
				 				   						.complement("")
				 				   						.build())
				 		   .personAuth(personAuthDTO)
				 		   .build();
	}
	
	@Test
	public void testSave() {
		
		HttpEntity<PersonDTO> request = new HttpEntity<PersonDTO>(personDTO);
		
		Mockito.when(this.authFeignClient.savePersonAuth(personAuthDTO)).thenReturn(ResponseEntity.ok(personAuthResponseDTO));
		Mockito.when(this.personService.save(personDTO)).thenReturn(personDTO);
		
		ResponseEntity<ResponseDTO<PersonDTO>> response = testRestTemplate.exchange("http://localhost:" + port + "/person", HttpMethod.POST, request, new ParameterizedTypeReference<ResponseDTO<PersonDTO>>() {});
	
		assertNotNull(response.getBody().getData());
		
	    assertEquals(201, response.getBody().getStatus());
	}
	
	@Test
	public void testUpdate() {
		
		HttpEntity<PersonDTO> request = new HttpEntity<PersonDTO>(personDTO);
		
		Mockito.when(this.authFeignClient.updatePersonAuth(personAuthDTO)).thenReturn(ResponseEntity.ok(personAuthResponseDTO));
		Mockito.when(this.personService.update(personDTO)).thenReturn(personDTO);
		
		ResponseEntity<ResponseDTO<PersonDTO>> response = testRestTemplate.exchange("http://localhost:" + port + "/person/", HttpMethod.PUT, request, new ParameterizedTypeReference<ResponseDTO<PersonDTO>>() {});
	
		assertNotNull(response.getBody().getData());
		
	    assertEquals(200, response.getBody().getStatus());
	}
	
	@Test
	public void testDeleteById() {
		
		Mockito.doCallRealMethod().when(this.personService).delete(1l);
		
		ResponseEntity<Void> response = testRestTemplate.exchange("http://localhost:" + port + "/person/1", HttpMethod.DELETE, null, new ParameterizedTypeReference<Void>() {});
	
		assertNull("Deletado com sucesso.", response.getBody());
	}
	
	@Test
	public void testFindById() {
		
		Mockito.when(this.personService.findById(1l)).thenReturn(personDTO);
		
		ResponseEntity<ResponseDTO<PersonDTO>> response = testRestTemplate.exchange("http://localhost:" + port + "/person/1", HttpMethod.GET, null, new ParameterizedTypeReference<ResponseDTO<PersonDTO>>() {});
	
		assertNotNull(response.getBody().getData());
		
	    assertEquals(200, response.getBody().getStatus());
	}
	
	@Test
	public void testFindAll() {
		
		Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_LIMIT, Sort.by(Direction.ASC, PAGE_ORDERED));
		
		List<PersonDTO> personList = new ArrayList<>();
		
		personList.add(personDTO);
		
		PageImpl<PersonDTO> personPage = new PageImpl<PersonDTO>(personList);

		Mockito.when(this.personService.findAll(pageable)).thenReturn(personPage);
		
		ResponseEntity<?> response = testRestTemplate.getForEntity("http://localhost:" + port + "/person/", ResponseDTO.class);
		
		assertNotNull(response.getBody());
		
		ResponseDTO<?> responseOk = (ResponseDTO<?>) response.getBody();

		assertNotNull(responseOk.getData());		
		assertEquals(200, responseOk.getStatus());
	}
}

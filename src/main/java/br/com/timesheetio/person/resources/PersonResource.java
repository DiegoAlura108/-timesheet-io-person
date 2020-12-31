package br.com.timesheetio.person.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.dto.ResponseDTO;
import br.com.timesheetio.person.service.PersonService;
import io.swagger.annotations.Api;

@Api(tags = {"Gestao de Pessoas"})
@RestController
@RequestMapping("/person")
public class PersonResource {

    private static final Logger logger = LoggerFactory.getLogger(PersonResource.class);
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<PersonDTO>> findById(@PathVariable("id") Long id){
		
		logger.info("FINDING PERSON OBJECT BY ID ...: {}", id);
		
		PersonDTO personFound = personService.findById(id);
		
		ResponseDTO<PersonDTO> response = new ResponseDTO<PersonDTO>();
		response.setData(personFound);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).findById(id)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).delete(id)).withRel("DELETE"));
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO<PersonDTO>> save(@RequestBody PersonDTO person) {
		
		logger.info("SAVING PERSON OBJECT ...: {}", person);
		
		PersonDTO personSaved = personService.save(person);
		
		ResponseDTO<PersonDTO> response = new ResponseDTO<PersonDTO>();
		response.setData(personSaved);
		response.setStatus(HttpStatus.CREATED.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).findById(personSaved.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).update(personSaved)).withRel("UPDATE"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).delete(personSaved.getId())).withRel("DELETE"));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO person) {
		
		logger.info("UPDATING PERSON OBJECT ...: {}", person);
		
		PersonDTO personUpdated = personService.update(person);
		
		ResponseDTO<PersonDTO> response = new ResponseDTO<PersonDTO>();
		response.setData(personUpdated);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).findById(personUpdated.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).update(personUpdated)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).delete(personUpdated.getId())).withRel("DELETE"));		
		
		return ResponseEntity.ok(person);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){

		logger.info("DELETING PERSON OBJECT ...: {}", id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

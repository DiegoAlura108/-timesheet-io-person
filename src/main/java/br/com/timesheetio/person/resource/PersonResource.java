package br.com.timesheetio.person.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.timesheetio.person.configuration.SwaggerConfig;
import br.com.timesheetio.person.dto.PersonDTO;
import br.com.timesheetio.person.dto.ResponseDTO;
import br.com.timesheetio.person.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = SwaggerConfig.TAG_PERSON_OPERATION)
@RestController
@RequestMapping("/person")
public class PersonResource {

    private static final Logger logger = LoggerFactory.getLogger(PersonResource.class);
	
	@Autowired
	private PersonService personService;

	@ApiOperation(value = "This operation is used to find all persons.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Persons founded."),
			@ApiResponse(code = 404, message = "Persons not found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@GetMapping
	public ResponseEntity<ResponseDTO<Page<PersonDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") int page, 
																@RequestParam(name = "limit", defaultValue = "12") int limit,
																@RequestParam(name = "direction", defaultValue = "asc") String direction,
																@RequestParam(name = "field", defaultValue = "id") String field){
		
		Direction directionSelected = direction.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(directionSelected, field));
		
		Page<PersonDTO> pagePersons = personService.findAll(pageable);
		
		ResponseDTO<Page<PersonDTO>> response = new ResponseDTO<>();
		response.setData(pagePersons);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).findAll(page, limit, direction, field)).withSelfRel());
		
		return ResponseEntity.ok(response);
	}
	

	@ApiOperation(value = "This operation is used to find persons by id.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Person founded."),
			@ApiResponse(code = 404, message = "Person not found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
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
	
	@ApiOperation(value = "This operation is used to save persons.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Persons saved."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 500, message = "Server error.")
	})
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

	
	@ApiOperation(value = "This operation is used to update persons.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Persons updated."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@PutMapping
	public ResponseEntity<ResponseDTO<PersonDTO>> update(@RequestBody PersonDTO person) {
		
		logger.info("UPDATING PERSON OBJECT ...: {}", person);
		
		PersonDTO personUpdated = personService.update(person);
		
		ResponseDTO<PersonDTO> response = new ResponseDTO<PersonDTO>();
		response.setData(personUpdated);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).findById(personUpdated.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).update(personUpdated)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonResource.class).delete(personUpdated.getId())).withRel("DELETE"));		
		
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "This operation is used to delete persons.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 204, message = "Person deleted."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "Person Not Found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){

		logger.info("DELETING PERSON OBJECT ...: {}", id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

package br.com.timesheetio.person.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.timesheetio.person.dto.PersonAuthDTO;
import br.com.timesheetio.person.dto.ResponseDTO;

@FeignClient(value= "${host.link.default}")
public interface AuthFeignClient {

	@RequestMapping(path = "/person-auth", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO<PersonAuthDTO>> savePersonAuth(@RequestBody PersonAuthDTO personDTO);
	
	@RequestMapping(path = "/person-auth/user-key", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO<PersonAuthDTO>> updatePersonAuth(@RequestBody PersonAuthDTO personDTO);
}

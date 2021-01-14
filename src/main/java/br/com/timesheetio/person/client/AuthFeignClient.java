package br.com.timesheetio.person.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.timesheetio.person.dto.PersonAuthDTO;

@FeignClient("auth")
public interface AuthFeignClient {

	@PostMapping
	public String savePersonAuth(PersonAuthDTO personDTO);
}

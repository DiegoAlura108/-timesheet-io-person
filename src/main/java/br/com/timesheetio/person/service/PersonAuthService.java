package br.com.timesheetio.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.timesheetio.person.client.AuthFeignClient;
import br.com.timesheetio.person.dto.PersonAuthDTO;
import br.com.timesheetio.person.dto.ResponseDTO;

@Service
public class PersonAuthService {

	@Autowired
	private AuthFeignClient authFeignClient;
	
	public  ResponseDTO<PersonAuthDTO> savePersonAuth(PersonAuthDTO authDTO) {
		
		return authFeignClient.savePersonAuth(authDTO).getBody();
	}
	
	public  ResponseDTO<PersonAuthDTO> updatePersonAuth(PersonAuthDTO authDTO) {
		
		return authFeignClient.updatePersonAuth(authDTO).getBody();
	}
}

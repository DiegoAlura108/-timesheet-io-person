package br.com.timesheetio.person.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embedded;

import br.com.timesheetio.person.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {
	
	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String socialRason;
	
	private String firstName;

	private String lastName;

	private String nickName;
	
	private Integer age;
	
	@Embedded
	private AddressDTO address;
	
	private String document;
	
	private PersonType personType;
	
	@Embedded
	private PersonAuthDTO personAuth;
	
	private LocalDate createdDate;

	private LocalDate updatedDate;
}

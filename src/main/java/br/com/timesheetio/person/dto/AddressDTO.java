package br.com.timesheetio.person.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AddressDTO implements Serializable {
	
	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String street;
	
	private String neighborhood;

	private String number;
	
	private String complement;
	
	private String city;
	
	private String cep;
	
	private String reference;
}

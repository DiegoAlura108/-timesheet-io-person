package br.com.timesheetio.person.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonAuthDTO implements Serializable {
	
	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	private String password;
	
	private String personUserKey;
}

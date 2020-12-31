package br.com.timesheetio.person.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ObjectAlreadyExistsException extends RuntimeException {
	
	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private final HttpStatus status;
	
	public ObjectAlreadyExistsException(String message, HttpStatus status) {

		super(message);
		this.status = status;
	}
}

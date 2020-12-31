package br.com.timesheetio.person.handler;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.timesheetio.person.dto.ExceptionResponseDTO;
import br.com.timesheetio.person.exceptions.ObjectAlreadyExistsException;
import br.com.timesheetio.person.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ApplicationHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ObjectAlreadyExistsException.class })
	public ResponseEntity<ExceptionResponseDTO> handleObjectAlreadyExistsException(ObjectAlreadyExistsException ex,
			WebRequest req) {
		
		ExceptionResponseDTO exceptionResponseDTO = ExceptionResponseDTO.builder().timestamp(new Date().getTime())
				.error(ex.getMessage()).path(req.getDescription(false)).trace(ExceptionUtils.getMessage(ex)).status(HttpStatus.BAD_REQUEST.value())
				.build();

		return new ResponseEntity<ExceptionResponseDTO>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ObjectNotFoundException.class })
	public ResponseEntity<ExceptionResponseDTO> handleObjectNotFoundException(ObjectNotFoundException ex,
			WebRequest req) {

		ExceptionResponseDTO exceptionResponseDTO = ExceptionResponseDTO.builder().timestamp(new Date().getTime())
				.error(ex.getMessage()).path(req.getDescription(false)).trace(ExceptionUtils.getMessage(ex)).status(HttpStatus.BAD_REQUEST.value())
				.build();

		return new ResponseEntity<ExceptionResponseDTO>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
	}
}

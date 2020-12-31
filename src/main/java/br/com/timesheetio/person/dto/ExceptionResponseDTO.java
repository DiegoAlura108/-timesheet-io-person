package br.com.timesheetio.person.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDTO implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	private Long timestamp;
	
	private String error;
	
	private String path;
	
	private String trace;
	
	private Integer status;
}

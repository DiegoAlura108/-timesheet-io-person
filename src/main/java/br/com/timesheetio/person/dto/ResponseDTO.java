package br.com.timesheetio.person.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResponseDTO<D> extends RepresentationModel<ResponseDTO<D>>{

	private int status;
	
	private D data;
}

package br.com.timesheetio.person.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseDTO<D> extends RepresentationModel<ResponseDTO<D>>{

	private Integer status;
	
	private D data;
}

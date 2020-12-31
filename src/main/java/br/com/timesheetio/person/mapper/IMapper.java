package br.com.timesheetio.person.mapper;

public interface IMapper <E, D>{

	public D convertEntityToDto(E e);
	
	public E convertDtoToEntity(D d);
}

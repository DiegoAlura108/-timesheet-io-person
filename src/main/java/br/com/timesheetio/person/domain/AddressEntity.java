package br.com.timesheetio.person.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ADDRESS")
public class AddressEntity implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "STREET")
	private String street;
	
	@Column(name= "NEIGHBORHOOD")
	private String neighborhood;
	
	@Column(name= "NUMBER")
	private Integer number;
	
	@Column(name= "COMPLEMENT")
	private String complement;
	
	@Column(name= "CITY")
	private String city;
	
	@Column(name= "CEP")
	private String cep;
	
	@Column(name= "REFERENCE")
	private String reference;
}

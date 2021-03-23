package br.com.timesheetio.person.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.com.timesheetio.person.enums.PersonType;
import lombok.Data;

@Data
@Entity
@Table(name = "PERSON")
public class PersonEntity implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "SOCIAL_RASON")
	private String socialRason;
	
	@Column(name= "FIRST_NAME")
	private String firstName;
	
	@Column(name= "LAST_NAME")
	private String lastName;
	
	@Column(name= "NICK_NAME")
	private String nickName;
	
	@Column(name= "AGE")
	private Integer age;

	@JoinColumn(name="ADDRESS_ID", unique = true)
	@OneToOne(cascade = CascadeType.ALL)
	private AddressEntity address;
	
	@Column(name= "DOCUMENT")
	private String document;
	
	@Column(name= "PERSON_TYPE")
	@Enumerated(EnumType.STRING)
	private PersonType personType;

	@Column(name= "PERSON_USER_KEY")
	private String personAuthUserKey;
	
	@Column(name= "CREATED_DATE")
	private LocalDate createdDate;
	
	@Column(name= "UPDATED_DATE")
	private LocalDate updatedDate;
	
	@PrePersist
	private void creationDate() {
		
		this.createdDate = LocalDate.now();
	}
	
	@PreUpdate
	private void updationDate() {
		
		this.updatedDate = LocalDate.now();
	}
}

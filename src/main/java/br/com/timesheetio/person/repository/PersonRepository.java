package br.com.timesheetio.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.timesheetio.person.domain.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long>{

}

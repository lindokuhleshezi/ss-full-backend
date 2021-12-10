/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.service.faces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;

import za.ac.ss.entities.Person;
import za.ac.ss.exception.ResourceNotFoundException;

/**
 *
 * @author lindokuhle
 */

@Secured ({"ROLE_Admin", "ROLE_Employee", "ROLE_Customer"})
public interface CustomerService {
    
     public Optional<Person> findById(Long id) throws ResourceNotFoundException;
     public List<Person> findByIdNumber(String username);
     public Person save(Person person);
     public List<Person> findAll();
     public void delete(Person person);
     public List<Person> searchByIdnumber(String id);
     public Person update(Long id, Person tempPerson) throws ResourceNotFoundException;
     public Person findByPassport(String passport);
	 public List<Person> findAllByPassport(String passport);
	 public List<Person> findAllByPassportOrIdNumber(String value);
}

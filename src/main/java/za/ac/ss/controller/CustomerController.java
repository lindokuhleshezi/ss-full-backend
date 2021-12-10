/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import za.ac.ss.entities.Person;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.CustomerService;

/**
 *
 * @author lindokuhle
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class CustomerController {

    @Autowired private CustomerService profileService;

    @GetMapping
    public List<Person> findAll() {
        return this.profileService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Optional<Person> person = this.profileService.findById(id);
        return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
    }

    @GetMapping("/id-number/{id}")
    public List<Person> findByIdNumber(@PathVariable(value = "id") String username) throws ResourceNotFoundException {        
        return this.profileService.findByIdNumber(username);             
    }
    
     @GetMapping("/searchByIdNumber/{id}")
    public List<Person> searchByIdNumber(@PathVariable(value = "id") Optional<String> id) throws ResourceNotFoundException {        
        return this.profileService.searchByIdnumber(id.orElse("_"));             
    }
    
    @PostMapping("/create")
    public Person save(@Valid @RequestBody Person person) {
        return this.profileService.save(person);
    }
    
    @PutMapping("/update/{id}")
    public Person update(@PathVariable(value = "id") Long id, @Valid @RequestBody Person person) throws ResourceNotFoundException{
        return this.profileService.update(id,person);    
    }
    
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Person person = this.profileService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found for this id :: " + id));
        this.profileService.delete(person);
    }
        
    @GetMapping("/{passport}/customer")
    public ResponseEntity<Person> findByPassport(@PathVariable String passport) {
    	return new ResponseEntity<Person>(this.profileService.findByPassport(passport), HttpStatus.OK);
    }
    
    @GetMapping("/{passport}/all")
    public List<Person> findAllByPassport(@PathVariable String passport) {
		return this.profileService.findAllByPassport(passport);
    }
    
    @GetMapping("/{id}/passport/idnumber")
    public List<Person> findAllByPassportOrIdNumber(String passportOrIdNumber) {
		return this.profileService.findAllByPassportOrIdNumber(passportOrIdNumber);
    }
}

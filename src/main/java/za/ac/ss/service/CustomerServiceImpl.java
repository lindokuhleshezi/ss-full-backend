/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.Address;
import za.ac.ss.entities.Person;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.AddressRepository;
import za.ac.ss.repository.CustomerRepository;
import za.ac.ss.service.faces.CustomerService;

/**
 *
 * @author lindokuhle
 * @author 
 * @version 0.01
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired private CustomerRepository profileDAO;
    @Autowired private AddressRepository addressRepository;
    //Autowired private ConverterService converterService;

    @Override
    public Optional<Person> findById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(this.profileDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Customer %s is not found", id))));
    }

    @Override
    @Transactional
    public List<Person> findByIdNumber(String username) {
    	List<Person> personList = this.profileDAO.searchByIdnumber(username);
    	return personList;
		//return personList.stream().map(this.converterService::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Person save(Person person) {
    	Person personDetails = null;
    	if (!isIDNumberExist(person.getIdNumber())) {
    		personDetails = Person.builder()
    							.id(person.getId())
    							.firstName(person.getFirstName())
    							.lastName(person.getLastName())
    							.passport(person.getPassport())
    							.fullName(person.getFirstName() + " " + person.getLastName())
    							.email(person.getEmail()).idNumber(person.getIdNumber())
    							.contact(person.getContact())
    							.contactPerson(person.getContactPerson())
    							.address(person.getAddress())
    							.build();
    		
            return this.profileDAO.save(personDetails);
    	} else {
    		throw new IllegalStateException(String.format("ID Number %s already exist", person.getIdNumber()));
    	}
    }

    @Override
    public List<Person> findAll() {
        return this.profileDAO.findAll();
    }

    @Override
    public void delete(Person person) {
        profileDAO.delete(person);
    }

    @Override
    public List<Person> searchByIdnumber(String id) {
    	List<Person> personData = profileDAO.findAll();
    	log.info("personData " + personData);
    	return personData.stream().filter(p -> p.getIdNumber().contains(id)).collect(Collectors.toList());
    }

    
	@Override
	public Person update(Long id, Person tempPerson) throws ResourceNotFoundException {
		Person personData = profileDAO.findById(id).orElseThrow(() -> new IllegalStateException("record not found"));
		personData.setId(id);
		personData.setFirstName(tempPerson.getFirstName());
    	personData.setLastName(tempPerson.getLastName());
    	personData.setFullName(tempPerson.getFirstName() + "-" + tempPerson.getLastName());
    	personData.setEmail(tempPerson.getEmail());
    	personData.setIdNumber(tempPerson.getIdNumber());
    	personData.setPassport(tempPerson.getPassport());
    	personData.setContact(tempPerson.getContact());
    	personData.setContactPerson(tempPerson.getContactPerson());
    	personData.setAddress(getAddress(tempPerson.getAddress()));
    	personData.setVersion(personData.getVersion());
    	Person updatedPerson = profileDAO.save(personData);
    	return updatedPerson;
	}
	
	private Address getAddress(Address address) {
		Optional<Address> addressData = this.addressRepository.findById(address.getId());
		log.info("address " + addressData.toString());
		if (addressData.isPresent()) {
			//addressData.ifPresent((data) -> {
			address.setId(addressData.get().getId());
			address.setVersion(addressData.get().getVersion());
			//});
		}
		return address;
	}
	
    private boolean isIDNumberExist(String idNumber) {
    	log.info("test " + profileDAO.existsByIdNumber(idNumber));
    	return profileDAO.existsByIdNumber(idNumber);
    }

    @Override
    public Person findByPassport(String passport) {
		return this.profileDAO.findByPassport(passport);
	}
    
    @Override
    public List<Person> findAllByPassport(String passport) {
    	List<Person> personData = profileDAO.findAll();
    	return personData.stream().filter(p -> p.getPassport().contains(passport)).collect(Collectors.toList());
    }
    
    @Override
    public List<Person> findAllByPassportOrIdNumber(String value) {
    	List<Person> personData = profileDAO.findAll();
    	return personData.stream().filter(p -> p.getPassport().contains(value) || p.getIdNumber().contains(value)).collect(Collectors.toList());
    }
}

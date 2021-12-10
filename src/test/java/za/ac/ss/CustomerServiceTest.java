/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.ss;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import za.ac.ss.entities.Address;
import za.ac.ss.entities.Person;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.CustomerRepository;
import za.ac.ss.service.faces.CustomerService;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @MockBean
    private CustomerRepository repo;
    
    @InjectMocks
    private CustomerService service;
    
    @Test
    public void testUpdate() throws ResourceNotFoundException {
    	Person personData = new Person();
    	Address address = new Address();
    	personData.setAddress(address);
    	
    	Mockito.when(repo.save(personData)).thenReturn(personData);
    	
    	service.update(personData.getId(), personData);
    	
    	assertTrue(true);
    }
    
}

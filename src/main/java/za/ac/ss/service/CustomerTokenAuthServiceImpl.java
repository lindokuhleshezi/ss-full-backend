package za.ac.ss.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.CustomerToken;
import za.ac.ss.entities.Person;
import za.ac.ss.repository.CustomerRepository;
import za.ac.ss.repository.CustomerTokenRepository;
import za.ac.ss.service.faces.CustomerTokenAuthService;

@Slf4j
@Service
public class CustomerTokenAuthServiceImpl implements CustomerTokenAuthService {
    
	@Autowired private CustomerRepository repository;
    @Autowired private CustomerTokenRepository tokenRepository;
    
    @Override
    @Transactional
	public CustomerToken init(String idNumber) {
    	Optional<Person> person = findCustomerByIdNumber(idNumber);
    	log.info("person " + person.get());
    	if (person.isPresent()) {
    		return findOrCreate(person.get().getIdNumber());
    	} else {
    		throw new IllegalStateException("Something went wrong");
    	}
    }

	private Optional<Person> findCustomerByIdNumber(String idNumber) {
		Optional<Person> person = this.repository.findByIdNumber(idNumber);
		person.orElseThrow(() -> new IllegalStateException("Customer doesn't exist"));
		return person;
	}

	private CustomerToken findOrCreate(String idNumber) {
		CustomerToken tokenData = new CustomerToken();
		if (findTokenByIdNumber(idNumber).isPresent()) {
			tokenData = findTokenByIdNumber(idNumber).get();
		} else {
			tokenData = createCustomerAuth(idNumber, tokenData);
		}
		return tokenData;
	}

	private CustomerToken createCustomerAuth(String idNumber, CustomerToken tokenData) {
		try {
			if (idNumber != null) {
				CustomerToken customerToken = CustomerToken.builder().idNumber(idNumber).token(UUID.randomUUID())
						.createdDateTime(LocalDateTime.now()).build();
				tokenRepository.save(customerToken);
				tokenData = customerToken;
			}
		} catch (Exception e) {
			log.error("Unable to create customer auth {}", e);
			throw new IllegalStateException("Unable to create customer auth {}", e);
		}
		return tokenData;
	}

	private Optional<CustomerToken> findTokenByIdNumber(String idNumber) {
		Optional<CustomerToken> cusToken = this.tokenRepository.findByIdNumber(idNumber);
		return cusToken;
	}
	
	@Override
	public boolean tokenExpired(UUID token) {
		long timeElapsed = Duration.between(findToken(token).get().getCreatedDateTime(), LocalDateTime.now()).toMinutes();
		log.info("time elapsed " + timeElapsed);
		return timeElapsed < 30L ? true : false;
	}

	@Override
	public Optional<CustomerToken> findToken(UUID token) {
		return this.tokenRepository.findByToken(token);
	}

	@Override
	public boolean existToken(UUID token) {
		return findToken(token).isPresent();
	}

	@Override
	public void deleteByToken(UUID token) {
		this.tokenRepository.deleteByToken(token);	
	}

}

package za.ac.ss.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.CustomerToken;
import za.ac.ss.entities.Person;
import za.ac.ss.entities.Users;
import za.ac.ss.repository.CustomerRepository;
import za.ac.ss.repository.UsersRepository;
import za.ac.ss.service.faces.CustomerTokenAuthService;
import za.ac.ss.service.faces.ExternalAuthService;
import za.ac.ss.service.faces.OTPService;

@Slf4j
@Service
public class ExternalUserServiceImpl implements ExternalAuthService {

	@Autowired UsersRepository userRepository;
	@Autowired CustomerTokenAuthService customerToken;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired CustomerRepository customerRepository;
	@Autowired OTPService otpService; 
	
	@Override
	@Transactional
	public void createAccount(Users users, UUID token) {
		if (this.customerToken.existToken(token)) {
			Optional<Person> personDetails = getPersonData(token);
			Optional<Users> userhasAccount = findUserByPerson(personDetails.get().getId());
			String clientRole = "CUSTOMER";
			findOrCreate(users, token, personDetails, userhasAccount, clientRole);
		} else  {
			log.error(String.format("Invalid token %s", token));
			throw new IllegalStateException(String.format("Invalid token %s", token));
		}
	}

	private void findOrCreate(Users users, UUID token, Optional<Person> personDetails, Optional<Users> userhasAccount,
			String clientRole) {
		if (!userhasAccount.isPresent()) {
			Users userData = userData(users, personDetails);
			sendEmail(personDetails);
			this.userRepository.save(userData);
			customerToken.deleteByToken(token);
		}else  {
			log.error("User already exist");
			throw new IllegalStateException(String.format("User already exist"));
		}
	}

	private void sendEmail(Optional<Person> personDetails) {
		otpService.init(personDetails.get().getEmail());
	}

	private Users userData(Users users, Optional<Person> personDetails) {
		Users userData = Users.builder()
				.id(users.getId())
				.username(personDetails.get().getEmail())
				.email(personDetails.get().getEmail())
				.person(personDetails.get())
				.roles(users.getRoles())
				.enabled(0)
				.password(passwordEncoder.encode(users.getPassword())).build();
		return userData;
	}
	
	private Optional<Users> findUserByPerson(Long id) {
		return this.userRepository.findByPersonId(id);
	}

	private Optional<Person> getPersonData(UUID token) {
		Optional<CustomerToken> customerTokenData = customerToken.findToken(token);
		Optional<Person> customerData = null;
		String idNumber = null;
		if (customerTokenData != null)
			idNumber = customerTokenData.get().getIdNumber();
			customerData = this.customerRepository.findByIdNumber(idNumber);
		customerData.isPresent();
		customerData.orElseThrow(() -> new IllegalStateException("Customer doesn't exist"));
		return customerData;
	}
	
	@Override
	public UUID createAuth(String idNumber) {
		CustomerToken cusData = customerToken.init(idNumber);
		return cusData.getToken();	
	}
}

package za.ac.ss.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.Person;
import za.ac.ss.entities.Role;
import za.ac.ss.entities.Users;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.CustomerRepository;
import za.ac.ss.repository.UsersRepository;
import za.ac.ss.service.faces.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository usersDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private ConverterService converterService;
	@Autowired
	private CustomerRepository customerRepository;

	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<Users> getUsers() {
		List<Users> userList = this.usersDao.findAll();
		return userList;
		// return
		// userList.stream().map(this.converterService::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<Users> getUsers(Long id) {
		Optional<Users> userData = this.usersDao.findById(id);
		userData.orElseThrow(() -> new UsernameNotFoundException("User is not found"));
		return userData;
		// return userData.map(this.converterService::convertToDTO).get();
	}

	@Override
	@Transactional
	public void saveUsers(Users users) {
		Optional<Users> isUsernameExist = this.usersDao.findByUsername(users.getUsername());
		Optional<Person> person = this.customerRepository.findById(users.getPerson().getId());
		Users userDetails = userData(users, users.getRoles(), person);

		if (!isUsernameExist.isPresent()) {
			this.usersDao.save(userDetails);
		}else  {
			log.error("User already exist");
			throw new IllegalStateException(String.format("User already exist"));
		}
	}

	private Users userData(Users users, List<Role> set, Optional<Person> person) {
		return Users.builder()
				.id(users.getId())
				.username(users.getUsername())
				.roles(set)
				.person(person.get())
				.password(this.passwordEncoder.encode(users.getPassword()))
				.enabled(1)
				.email(users.getEmail())
				.build();
	}

	@Override
	@Transactional
	public Users updateUsers(Long id, Users users) throws ResourceNotFoundException {
		Users userData = this.usersDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("record not found"));
		userData.setId(userData.getId());
		userData.setEmail(users.getEmail());
		userData.setPassword(this.passwordEncoder.encode(users.getPassword()));
		userData.setRoles(users.getRoles());
		userData.setLastName(users.getLastName());
		userData.setVersion(users.getVersion());
		return this.usersDao.save(userData);
	}

	@Override
	public void deleteById(Long id) throws ResourceNotFoundException {
		Users userData = this.usersDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("record not found"));
		log.info("user: " + userData);
		if (userData != null) {
			this.usersDao.deleteById(id);
		}
	}

	@Override
	public Optional<Users> findByUsername(String username) throws ResourceNotFoundException {
		Optional<Users> users = this.usersDao.findByUsername(username);
		users.isPresent();
		users.orElseThrow(() -> new ResourceNotFoundException("user is not found!"));
		log.info("users " + users);
		return users;
		// return users.map(this.converterService::convertToDTO).get();
	}

	@Override
	public String revokeAccess(String tokenId) {
		tokenServices.revokeToken(tokenId);
		return tokenId;
	}

}

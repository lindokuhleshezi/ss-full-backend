package za.ac.ss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.CustomUserDetails;
import za.ac.ss.entities.Users;
import za.ac.ss.repository.UsersRepository;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepostory;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> usersOptional = usersRepostory.findByUsername(username);
		usersOptional.orElseThrow(() -> new UsernameNotFoundException("Username is not found"));
		log.info("{}", usersOptional.get());
		return usersOptional.map(CustomUserDetails::new).get();
	}
	
}

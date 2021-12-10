package za.ac.ss.service.faces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;

import za.ac.ss.entities.Users;
import za.ac.ss.exception.ResourceNotFoundException;


@Secured ({"ROLE_Admin", "ROLE_Employee", "ROLE_Customer"})
public interface UserService {

	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public List<Users> getUsers();
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public Optional<Users> getUsers(Long id);
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public void saveUsers(Users users) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public Users updateUsers(Long id, Users users) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public void deleteById(Long id) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public Optional<Users> findByUsername(String username) throws ResourceNotFoundException;
	@Secured ({"ROLE_Admin", "ROLE_Employee"})
	public String revokeAccess(String tokenId);
}


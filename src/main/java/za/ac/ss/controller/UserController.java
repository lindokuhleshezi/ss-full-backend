package za.ac.ss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.entities.Users;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<Users> getUsers() {
		List<Users> users  = new ArrayList<>();
		users = this.userService.getUsers();
		return users;
	}

	@GetMapping("/{id}")
	public Optional<Users> getUser(@PathVariable Long id) {
		return this.userService.getUsers(id);
	}
   @GetMapping("/username/{username}")
	public Optional<Users> findByUsername(@PathVariable String username) throws ResourceNotFoundException {
		return this.userService.findByUsername(username);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		this.userService.deleteById(id);
	}

	@PutMapping("/update/{id}")
	public Users update(@PathVariable Long id, @RequestBody Users user) throws ResourceNotFoundException {
		this.userService.updateUsers(id, user);
		return user;
	}

	@PostMapping
	public void save(@Valid @RequestBody Users user) throws ResourceNotFoundException {
		this.userService.saveUsers(user);
	}

	@DeleteMapping("/logout/{tokenId}")
	public void revokeAccess(@PathVariable String tokenId) {
		this.userService.revokeAccess(tokenId);
	}

}

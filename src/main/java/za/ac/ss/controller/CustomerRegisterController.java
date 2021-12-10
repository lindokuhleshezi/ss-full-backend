package za.ac.ss.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.entities.Users;
import za.ac.ss.service.faces.ExternalAuthService;

@RestController
@RequestMapping("/mobi")
public class CustomerRegisterController {
	
	@Autowired ExternalAuthService externalAuthService;
	
	@PostMapping("/account/{token}")
	public void createAccount(@RequestBody Users users, @PathVariable UUID token) {
		this.externalAuthService.createAccount(users, token);
	}
}

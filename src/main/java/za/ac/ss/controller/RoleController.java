package za.ac.ss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.enums.Roles;


@RequestMapping("/roles")
@RestController
public class RoleController {
	
	@GetMapping("/list")
	public Roles[] getRoles() {
		return Roles.values();
	}
}

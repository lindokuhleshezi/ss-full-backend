package za.ac.ss.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.ResetPasswordService;

@RestController
@RequestMapping("/users")
public class ResetPasswordController {

	@Autowired private ResetPasswordService resetPasswordService;

	@GetMapping("/forgot-password/{username}")
	public void forgotPassword(@PathVariable String username, HttpServletRequest request) throws ResourceNotFoundException, MalformedURLException, UnsupportedEncodingException  {
		this.resetPasswordService.forgotPassword(username, request);
	}
	
	@PostMapping("/change-password/{email}/{token}")
	public void resetPassword(@PathVariable("username") String email, @PathVariable String token, @RequestBody String password) throws ResourceNotFoundException {
		this.resetPasswordService.resetPassword(email, token, password);
	}
}

package za.ac.ss.service.faces;

import java.net.MalformedURLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import za.ac.ss.entities.Users;
import za.ac.ss.exception.ResourceNotFoundException;

public interface ResetPasswordService {

	public void forgotPassword(String username, HttpServletRequest request)
			throws MalformedURLException, ResourceNotFoundException;

	Optional<Users> resetPassword(String username, String token, String password) throws ResourceNotFoundException;

}
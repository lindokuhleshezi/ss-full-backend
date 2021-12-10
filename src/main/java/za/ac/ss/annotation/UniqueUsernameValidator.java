package za.ac.ss.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import za.ac.ss.repository.UsersRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired UsersRepository userDao;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return username != null && userDao.findByUsername(username) != null ? true : false;
	}

}

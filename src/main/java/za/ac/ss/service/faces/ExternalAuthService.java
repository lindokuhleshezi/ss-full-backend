package za.ac.ss.service.faces;

import java.util.UUID;

import org.springframework.security.access.annotation.Secured;

import za.ac.ss.entities.Users;

public interface ExternalAuthService {

	void createAccount(Users users, UUID token);

	UUID createAuth(String idNumber);

}
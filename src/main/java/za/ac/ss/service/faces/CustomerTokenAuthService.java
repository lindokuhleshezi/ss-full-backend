package za.ac.ss.service.faces;

import java.util.Optional;
import java.util.UUID;

import za.ac.ss.entities.CustomerToken;

public interface CustomerTokenAuthService {

	public CustomerToken init(String idNumber);
	public boolean tokenExpired(UUID token);
	public boolean existToken(UUID token);
	public Optional<CustomerToken>  findToken(UUID token);
	public void deleteByToken(UUID token);
}